package com.wasir.droid.core.data.repository

import com.wasir.droid.core.data.datasource.ImageDataSource
import com.wasir.droid.core.data.db.entity.ImageEntity
import com.wasir.droid.core.data.mapper.toEntity
import com.wasir.droid.core.data.mapper.toImageData
import com.wasir.droid.core.data.model.ImageData
import com.wasir.droid.core.data.model.ImageResponse
import com.wasir.droid.core.data.util.DispatchersProvider
import com.wasir.droid.core.domain.repository.ImageRepository
import com.wasir.droid.core.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.tartarus.snowball.ext.englishStemmer

class ImageRepositoryImpl(
    private val remote: ImageDataSource.Remote,
    private val local: ImageDataSource.Local,
    private val dispatchersProvider: DispatchersProvider,
    private val stemmer: englishStemmer
) : ImageRepository {

    override suspend fun search(
        query: String,
        pageToFetch: Int,
        pageSize: Int
    ): Flow<Resource<ImageResponse>> {
        return flow {
            try {
                val response = remote.search(query, pageToFetch, pageSize)
                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(Resource.Success(it))
                        saveToDatabase(it.imageLists?.map { imageData ->
                            imageData.toEntity(
                                imageData
                            )
                        })
                    } ?: emit(Resource.Error("An unknown error occured", null))
                }
            } catch (e: Exception) {
                emit(Resource.Error("Couldn't reach the server. Check your internet connection"))
            }
        }.flowOn(dispatchersProvider.getIO())
    }

    override suspend fun searchOffline(query: String): Flow<Resource<ImageResponse>> {
        return flow {
            try {
                emit(getImagesFromLocalDb(query))
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage))
            }

        }.flowOn(dispatchersProvider.getIO())
    }

    private suspend fun saveToDatabase(imageLists: List<ImageEntity>?) {
        imageLists?.let { local.saveImages(it) }
    }

    private suspend fun getImagesFromLocalDb(query: String): Resource<ImageResponse> {
        val result = local.getImageList(applyStemmerOnQuery(query))
        val imageLists = mutableListOf<ImageData>()
        imageLists.addAll(result.map { imageEntity ->
            imageEntity.toImageData(imageEntity)
        })
        return Resource.Success(ImageResponse().apply {
            this.imageLists = imageLists
        })
    }

    /*apply stem before query to make search easier from room. e.g flowers will be flower after stem*/
    private fun applyStemmerOnQuery(query: String): String {
        stemmer.current = query
        stemmer.stem()
        return stemmer.current
    }


}
