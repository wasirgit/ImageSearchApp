package com.wasir.droid.imagesearchapp.di

import com.wasir.droid.core.domain.repository.ImageRepository
import com.wasir.droid.core.domain.usecase.SearchImages
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {
    @Provides
    @ViewModelScoped
    fun provideSearchUseCase(imageRepository: ImageRepository): SearchImages {
        return SearchImages(imageRepository)
    }
}