package com.wasir.droid.core.domain.repository

import com.wasir.droid.core.data.model.ImageResponse
import com.wasir.droid.core.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface ImageRepository {
    suspend fun search(query: String, pageToFetch: Int, pageSize: Int): Flow<Resource<ImageResponse>>
    suspend fun searchOffline(query: String): Flow<Resource<ImageResponse>>
}