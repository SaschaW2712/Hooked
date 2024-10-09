package com.saschaw.hooked.feature.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saschaw.hooked.core.data.repository.RavelryUserDataRepository
import com.saschaw.hooked.core.network.FavoritesListPaginated
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesScreenViewModel @Inject constructor(
    private val ravelryUserDataRepository: RavelryUserDataRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<FavoritesScreenUiState>(FavoritesScreenUiState.Loading)
    val uiState: StateFlow<FavoritesScreenUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                val favorites = ravelryUserDataRepository.getFavoritesList()
                _uiState.value = FavoritesScreenUiState.Success(favorites)
            } catch (e: Exception) {
                _uiState.value = FavoritesScreenUiState.Error(e)
            }
        }
    }
}

sealed interface FavoritesScreenUiState {
    data object Loading : FavoritesScreenUiState
    data class Success(val favorites: FavoritesListPaginated) : FavoritesScreenUiState
    data class Error(val exception: Exception) : FavoritesScreenUiState
}
