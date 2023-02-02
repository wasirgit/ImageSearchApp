package com.wasir.droid.core.data.api

import com.wasir.droid.core.data.model.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageApi {
    @GET("api/")
    suspend fun search(
        @Query("q") query: String,
        @Query("page") pageToFetch: Int,
        @Query("per_page") pageSize: Int,
    ): Response<ImageResponse>
}