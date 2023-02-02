package com.wasir.droid.core.data.repository

import com.wasir.droid.core.data.db.entity.ImageEntity
import com.wasir.droid.core.data.mapper.toEntity
import com.wasir.droid.core.data.mapper.toImageData
import com.wasir.droid.core.data.model.ImageData
import com.wasir.droid.core.data.model.ImageResponse
import com.wasir.droid.core.data.util.DispatchersProvider
import com.wasir.droid.core.data.util.MockData
import com.wasir.droid.core.domain.repository.ImageRepository
import com.wasir.droid.core.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FakeImageRepositoryImpl(
    private val dispatchersProvider: DispatchersProvider,
) :
    ImageRepository {
    private val imageDataCache = mutableListOf<ImageEntity>()
    private var shouldThrowError: Boolean = false
    fun showThrowError(shouldThrowError: Boolean) {
        this.shouldThrowError = shouldThrowError
    }

    override suspend fun search(
        query: String,
        pageToFetch: Int,
        pageSize: Int
    ): Flow<Resource<ImageResponse>> {
        return flow {
            if (!shouldThrowError) {
                val response = MockData.getMockImageResponse()
                saveToDatabase(response.imageLists?.map { imageData ->
                    imageData.toEntity(
                        imageData
                    )
                })
                emit(Resource.Success(response))
            } else {
                emit(Resource.Error("Couldn't reach the server. Check your internet connection"))
            }

        }.flowOn(dispatchersProvider.getIO())
    }

    private fun saveToDatabase(imageLists: List<ImageEntity>?) {
        imageLists?.let { imageDataCache.addAll(it) }
    }

    override suspend fun searchOffline(query: String): Flow<Resource<ImageResponse>> {
        return flow {
            try {
                val result = imageDataCache
                val imageLists = mutableListOf<ImageData>()
                imageLists.addAll(result.map { imageEntity ->
                    imageEntity.toImageData(imageEntity)
                })
                val imageResponse = ImageResponse().apply {
                    this.imageLists = imageLists
                }
                emit(Resource.Success(imageResponse))
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage))
            }
        }.flowOn(dispatchersProvider.getIO())
    }
}