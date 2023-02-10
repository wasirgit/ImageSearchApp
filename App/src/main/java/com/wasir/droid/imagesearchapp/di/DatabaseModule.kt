package com.wasir.droid.imagesearchapp.di

import android.content.Context
import androidx.room.Room
import com.wasir.droid.core.data.db.ImageDatabase
import com.wasir.droid.core.data.db.ImageSearchDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideImageDatabase(@ApplicationContext context: Context): ImageDatabase {
        return Room.databaseBuilder(context, ImageDatabase::class.java, "image.db").build()
    }

    @Provides
    fun provideImageDao(imageDatabase: ImageDatabase): ImageSearchDao {
        return imageDatabase.imageDao()
    }
}