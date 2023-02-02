package com.wasir.droid.core.data.datasource

import com.wasir.droid.core.data.db.ImageSearchDao
import com.wasir.droid.core.data.db.entity.ImageEntity

class ImageLocalDataSource constructor(private val imageSearchDao: ImageSearchDao) :
    ImageDataSource.Local {


    override suspend fun getImageList(query: String): List<ImageEntity> =
        imageSearchDao.getImageList(query)

    override suspend fun saveImages(images: List<ImageEntity>) {
        imageSearchDao.saveImages(images)
    }
}
