package com.wasir.droid.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wasir.droid.core.data.db.entity.ImageEntity

@Database(
    entities = [ImageEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ImageDatabase : RoomDatabase() {
    abstract fun imageDao(): ImageSearchDao
}