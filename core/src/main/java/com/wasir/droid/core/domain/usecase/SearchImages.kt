package com.wasir.droid.core.domain.usecase

import com.wasir.droid.core.data.model.ImageResponse
import com.wasir.droid.core.domain.repository.ImageRepository
import com.wasir.droid.core.domain.util.Resource
import kotlinx.coroutines.flow.Flow

class SearchImages(private val imageRepository: ImageRepository) {
    suspend fun search(
        query: String,
        pageToFetch: Int,
        pageSize: Int
    ): Flow<Resource<ImageResponse>> =
        imageRepository.search(query, pageToFetch, pageSize)

    suspend fun searchOffline(
        query: String,
    ): Flow<Resource<ImageResponse>> =
        imageRepository.searchOffline(query)
}