package com.wasir.droid.paybackcodingchallenge.di

import com.wasir.droid.core.data.datasource.ImageDataSource
import com.wasir.droid.core.data.repository.ImageRepositoryImpl
import com.wasir.droid.core.data.util.DispatchersProvider
import com.wasir.droid.core.domain.repository.ImageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import org.tartarus.snowball.ext.englishStemmer

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideImageRepository(
        remote: ImageDataSource.Remote,
        local: ImageDataSource.Local,
        dispatchersProvider: DispatchersProvider,
        stemmer: englishStemmer
    ): ImageRepository {
        return ImageRepositoryImpl(remote, local, dispatchersProvider, stemmer)
    }
}