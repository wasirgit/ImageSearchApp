package com.wasir.droid.paybackcodingchallenge.presentation.ui.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.wasir.droid.core.data.repository.FakeImageRepositoryImpl
import com.wasir.droid.core.data.util.CoroutineDispatcherProviderImpl
import com.wasir.droid.core.data.util.DispatchersProvider
import com.wasir.droid.core.data.util.MockData
import com.wasir.droid.core.domain.usecase.SearchImages
import com.wasir.droid.core.domain.util.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class ImageSearchViewModelTest {
    @get:Rule
    var taskInstantExecutorRule = InstantTaskExecutorRule()
    private lateinit var searchImages: SearchImages

    private lateinit var viewModel: ImageSearchViewModel
    private lateinit var dispatcherProvider: DispatchersProvider
    private lateinit var fakeImageRepositoryImpl: FakeImageRepositoryImpl

    @Before
    fun setUp() {
        dispatcherProvider = CoroutineDispatcherProviderImpl()
        fakeImageRepositoryImpl = FakeImageRepositoryImpl(dispatcherProvider)
        searchImages = SearchImages(fakeImageRepositoryImpl)
        viewModel = ImageSearchViewModel(searchImages, dispatcherProvider)
    }

    @Test
    fun `load data successfully`() = runBlocking {
        val expected = MockData.getMockImageResponse()
        val result = searchImages.search("", 0, 0).first()
        assert(result.data == expected)
    }

    @Test
    fun `error during data loading`() = runBlocking {
        fakeImageRepositoryImpl.showThrowError(true)
        val result = searchImages.search("", 0, 0).first()
        assert(result is Resource.Error)
    }

    @Test
    fun `load data successfully from local database`() = runBlocking {
        val result = searchImages.searchOffline("").first()
        assert(result is Resource.Success)
        assertTrue(result.data != null)
        assertTrue(result.data?.imageLists != null)
    }

    @Test
    fun `check loading state of UiState `() {
        assertEquals(null, viewModel.getUiState().value)
        viewModel.searchImages()
        assertEquals(ImageSearchViewModel.UiState.Loading, viewModel.getUiState().value)
    }

    @After
    fun tearDown() {
    }

}