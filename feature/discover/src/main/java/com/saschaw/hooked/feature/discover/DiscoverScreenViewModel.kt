package com.saschaw.hooked.feature.discover

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saschaw.hooked.core.data.repository.RavelrySearchRepository
import com.saschaw.hooked.core.model.lists.search.SearchQueryWithResults
import com.saschaw.hooked.feature.discover.DiscoverScreenUiState.Loading
import com.saschaw.hooked.feature.discover.DiscoverScreenUiState.Error
import com.saschaw.hooked.feature.discover.DiscoverScreenUiState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DiscoverScreenViewModel @Inject constructor(
    private val ravelrySearchRepository: RavelrySearchRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<DiscoverScreenUiState>(Loading)
    val uiState: StateFlow<DiscoverScreenUiState> = _uiState.asStateFlow()

    fun onSearch(query: String) {
        _uiState.value = Loading

        viewModelScope.launch {
            try {
                val searchResults = ravelrySearchRepository.search(query)

                _uiState.value =
                    if (searchResults != null && searchResults.patterns.isNotEmpty()) {
                        Success(SearchQueryWithResults(query, searchResults))
                    } else {
                        Error
                    }
            } catch (e: Exception) {
                Log.e("SearchScreenViewModel", "Couldn't load search results", e)
                _uiState.value = Error
            }
        }
    }

    fun getHotRightNow() {
        _uiState.value = Loading

        viewModelScope.launch {
            try {
                val searchResults = ravelrySearchRepository.getHotRightNow()

                _uiState.value =
                    if (searchResults != null && searchResults.patterns.isNotEmpty()) {
                        Success(SearchQueryWithResults(null, searchResults))
                    } else {
                        Error
                    }
            } catch (e: Exception) {
                Log.e("SearchScreenViewModel", "Couldn't load search results", e)
                _uiState.value = Error
            }
        }
    }
}

sealed interface DiscoverScreenUiState {
    data object Loading : DiscoverScreenUiState
    data class Success(val searchQueryWithResults: SearchQueryWithResults) : DiscoverScreenUiState
    data object Error : DiscoverScreenUiState
}