package com.saschaw.hooked.feature.patterndetails

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saschaw.hooked.core.data.repository.RavelryPatternDetailsRepository
import com.saschaw.hooked.core.model.pattern.PatternFull
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatternDetailsScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val ravelryPatternDetailsRepository: RavelryPatternDetailsRepository
) : ViewModel() {
    private val patternId: Int = savedStateHandle.get<String>("patternId")?.toIntOrNull() ?: -1

    private val _uiState = MutableStateFlow<PatternDetailsScreenUiState>(PatternDetailsScreenUiState.Loading)
    val uiState: StateFlow<PatternDetailsScreenUiState> = _uiState.asStateFlow()

    fun onLoadPattern() {
        _uiState.value = PatternDetailsScreenUiState.Loading

        viewModelScope.launch {
            try {
                    val result = ravelryPatternDetailsRepository.getPatternDetails(patternId)

                    _uiState.value =
                        if (result != null) {
                            PatternDetailsScreenUiState.Success(result)
                        } else {
                            PatternDetailsScreenUiState.Error
                        }
            } catch (e: Exception) {
                Log.e("PatternDetailsScreenVM", "Couldn't load pattern details", e)
                _uiState.value = PatternDetailsScreenUiState.Error
            }
        }
    }
}

sealed interface PatternDetailsScreenUiState {
    data object Loading : PatternDetailsScreenUiState
    data class Success(val patternDetails: PatternFull) : PatternDetailsScreenUiState
    data object Error : PatternDetailsScreenUiState
}