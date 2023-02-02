package com.wasir.droid.core.data.datasource

import com.wasir.droid.core.data.api.ImageApi
import com.wasir.droid.core.data.model.ImageResponse
import retrofit2.Response

class ImageRemoteDataSource(
    private val imageApi: ImageApi
) : ImageDataSource.Remote {
    override suspend fun search(
        query: String,
        pageToFetch: Int,
        pageSize: Int
    ): Response<ImageResponse> {
        return imageApi.search(query, pageToFetch, pageSize)
    }
}