package com.wasir.droid.paybackcodingchallenge.di

import android.content.Context
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import com.wasir.droid.paybackcodingchallenge.App
import com.wasir.droid.paybackcodingchallenge.presentation.util.NetworkConnectivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @ApplicationContext
    fun provideApplicationContext(@ApplicationContext context: Context): App {
        return context.applicationContext as App
    }

    @Provides
    @Singleton
    fun provideImageLoader(@ApplicationContext context: Context): ImageLoader {
        return ImageLoader.Builder(context)
            .memoryCache {
                MemoryCache.Builder(context)
                    .maxSizePercent(0.25)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(context.cacheDir.resolve("image_cache"))
                    .maxSizePercent(.5)
                    .build()
            }
            .build()
    }

    @Provides
    @Singleton
    fun isNetworkAvailable(@ApplicationContext context: Context): NetworkConnectivity {
        return NetworkConnectivity(context)
    }


}