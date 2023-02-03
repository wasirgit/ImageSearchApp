package com.wasir.droid.imagesearchapp.presentation.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wasir.droid.core.data.model.ImageData
import com.wasir.droid.core.data.util.DispatchersProvider
import com.wasir.droid.core.domain.usecase.SearchImages
import com.wasir.droid.core.domain.util.Resource
import com.wasir.droid.imagesearchapp.presentation.util.Constant
import com.wasir.droid.imagesearchapp.presentation.util.LiveDataSingleEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageSearchViewModel @Inject internal constructor(
    private val searchImages: SearchImages,
    private val dispatchers: DispatchersProvider,
) : ViewModel() {
    private var pageToFetch = 1 /* which page will load from server*/
    private var totalPage = 1
    var removePreviousData = false
    var searchQuery = Constant.DEFAULT_SEARCH_QUERY
        set(value) {
            field = value
            pageToFetch = 1
        }
    private val uiState: LiveDataSingleEvent<UiState> = LiveDataSingleEvent()
    private var searchJob: Job? = null
    var searchResult: MutableList<ImageData> =
        ArrayList() /*storing search result to support configuration changes*/

    /*UiState to interact with UI*/
    sealed class UiState {
        data class Success(val data: List<ImageData>?) : UiState()
        data class Error(val message: String?) : UiState()
        object Loading : UiState()
    }

    /* Checking if it is last page, calculating next page to load*/
    private fun updatePageSize() {
        pageToFetch++
        if (totalPage < pageToFetch - 1) {
            pageToFetch = Constant.NO_MORE_PAGE
        }
    }

    /*search images from server*/
    fun searchImages() {
        if (pageToFetch != Constant.NO_MORE_PAGE) {
            searchJob?.cancel()
            uiState.value = UiState.Loading
            searchJob = viewModelScope.launch(dispatchers.getIO()) {
                searchImages.search(searchQuery, pageToFetch, Constant.PAGE_SIZE)
                    .collect { result ->
                        when (result) {
                            is Resource.Success -> {
                                result.data?.let {
                                    totalPage = (it.totalHits / Constant.PAGE_SIZE)
                                }
                                uiState.postValue(UiState.Success(result.data?.imageLists))
                                updatePageSize()
                            }
                            is Resource.Error -> {
                                uiState.postValue(UiState.Error(result.message))
                            }
                        }
                    }
            }
        }
    }

    /*search images from local database*/
    fun searchImageOffline() {
        searchJob?.cancel()
        uiState.value = UiState.Loading
        searchJob = viewModelScope.launch(dispatchers.getIO()) {
            searchImages.searchOffline(searchQuery)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            uiState.postValue(UiState.Success(result.data?.imageLists))
                        }
                        is Resource.Error -> {
                            uiState.postValue(UiState.Error(result.message))
                        }
                    }
                }
        }
    }

    fun getUiState() = uiState
}
