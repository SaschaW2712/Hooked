package com.saschaw.hooked.feature.patterndetails

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.saschaw.hooked.core.datastore.PreferencesDataSource
import com.saschaw.hooked.core.model.PatternListItem
import com.saschaw.hooked.core.model.SearchWithResults
import com.saschaw.hooked.core.network.HookedNetworkDataSource
import com.saschaw.hooked.feature.patterndetails.navigation.PatternDetailsRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatternDetailsScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val preferences: PreferencesDataSource,
    private val network: HookedNetworkDataSource
) : ViewModel() {
    private val patternDetailsRoute: PatternDetailsRoute = savedStateHandle.toRoute()

    private val selectedPatternId = patternDetailsRoute.patternId

    private val _uiState = MutableStateFlow<PatternDetailsScreenUiState>(PatternDetailsScreenUiState.Loading)
    val uiState: StateFlow<PatternDetailsScreenUiState> = _uiState.asStateFlow()

    fun onLoadPattern() {
        _uiState.value = PatternDetailsScreenUiState.Loading

        viewModelScope.launch {
            try {
                // TODO HKD-30: Load real details
//                val pattern =

//                _uiState.value =
//                    if (searchResults != null && searchResults.patterns.isNotEmpty()) {
//                        Success(SearchWithResults(query, searchResults))
//                    } else {
//                        Error
//                    }
            } catch (e: Exception) {
                Log.e("PatternDetailsScreenVM", "Couldn't load pattern details", e)
                _uiState.value = PatternDetailsScreenUiState.Error
            }
        }
    }
}

sealed interface PatternDetailsScreenUiState {
    data object Loading : PatternDetailsScreenUiState
    data class Success(val patternDetails: String) : PatternDetailsScreenUiState
    data object Error : PatternDetailsScreenUiState
}