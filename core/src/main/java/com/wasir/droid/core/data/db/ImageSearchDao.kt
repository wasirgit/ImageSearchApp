package com.wasir.droid.core.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wasir.droid.core.data.db.entity.ImageEntity

@Dao
interface ImageSearchDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveImages(images: List<ImageEntity>)

    @Query("SELECT * FROM images WHERE tags LIKE '%' || :query || '%' OR pageURL LIKE '%' || :query || '%'")
    fun getImageList(query: String): List<ImageEntity>
}
