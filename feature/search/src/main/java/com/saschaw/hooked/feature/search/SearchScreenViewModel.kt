package com.saschaw.hooked.feature.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saschaw.hooked.core.data.repository.RavelrySearchRepository
import com.saschaw.hooked.core.model.SearchWithResults
import com.saschaw.hooked.feature.search.SearchScreenUiState.Loading
import com.saschaw.hooked.feature.search.SearchScreenUiState.Error
import com.saschaw.hooked.feature.search.SearchScreenUiState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val ravelrySearchRepository: RavelrySearchRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<SearchScreenUiState>(Loading)
    val uiState: StateFlow<SearchScreenUiState> = _uiState.asStateFlow()

    fun onSearch(query: String) {
        _uiState.value = Loading

        viewModelScope.launch {
            try {
                val searchResults = ravelrySearchRepository.search(query)

                _uiState.value =
                    if (searchResults != null && searchResults.patterns.isNotEmpty()) {
                        Success(SearchWithResults(query, searchResults))
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
                        Success(SearchWithResults(null, searchResults))
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

sealed interface SearchScreenUiState {
    data object Loading : SearchScreenUiState
    data class Success(val searchWithResults: SearchWithResults) : SearchScreenUiState
    data object Error : SearchScreenUiState
}