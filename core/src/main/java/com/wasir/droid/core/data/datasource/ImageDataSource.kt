package com.wasir.droid.core.data.datasource

import com.wasir.droid.core.data.db.entity.ImageEntity
import com.wasir.droid.core.data.model.ImageResponse
import retrofit2.Response


interface ImageDataSource {
    /*search images in server*/
    interface Remote {
        suspend fun search(query: String, pageToFetch: Int, pageSize: Int): Response<ImageResponse>
    }

    /*search images in local database*/
    interface Local {
        suspend fun getImageList(query: String): List<ImageEntity>
        suspend fun saveImages(images: List<ImageEntity>)
    }
}