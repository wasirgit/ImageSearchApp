package com.wasir.droid.imagesearchapp.di

import com.wasir.droid.core.data.api.ImageApi
import com.wasir.droid.core.data.datasource.ImageDataSource
import com.wasir.droid.core.data.datasource.ImageLocalDataSource
import com.wasir.droid.core.data.datasource.ImageRemoteDataSource
import com.wasir.droid.core.data.db.ImageSearchDao
import com.wasir.droid.core.data.util.CoroutineDispatcherProviderImpl
import com.wasir.droid.core.data.util.DispatchersProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import org.tartarus.snowball.ext.englishStemmer

@Module
@InstallIn(ViewModelComponent::class)
class DataModule {
    @Provides
    @ViewModelScoped
    fun provideLocalDataSource(
        imageSearchDao: ImageSearchDao
    ): ImageDataSource.Local {
        return ImageLocalDataSource(imageSearchDao)
    }

    @Provides
    @ViewModelScoped
    fun provideRemoteDataSource(
        imageApi: ImageApi
    ): ImageDataSource.Remote {
        return ImageRemoteDataSource(imageApi)
    }


    @ViewModelScoped
    @Provides
    fun provideDispatcher(): DispatchersProvider {
        return CoroutineDispatcherProviderImpl()
    }

    @ViewModelScoped
    @Provides
    fun provideStemmer(): englishStemmer {
        return englishStemmer()
    }
}