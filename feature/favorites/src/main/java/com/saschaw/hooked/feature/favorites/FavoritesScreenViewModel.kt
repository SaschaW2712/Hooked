package com.saschaw.hooked.feature.favorites

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saschaw.hooked.core.data.repository.RavelryUserDataRepository
import com.saschaw.hooked.core.model.lists.favorites.FavoritesListPaginated
import com.saschaw.hooked.feature.favorites.FavoritesScreenUiState.Error
import com.saschaw.hooked.feature.favorites.FavoritesScreenUiState.Loading
import com.saschaw.hooked.feature.favorites.FavoritesScreenUiState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesScreenViewModel @Inject constructor(
    private val ravelryUserDataRepository: RavelryUserDataRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<FavoritesScreenUiState>(Loading)
    val uiState: StateFlow<FavoritesScreenUiState> = _uiState.asStateFlow()

    init {
        refreshFavorites()
    }

    fun refreshFavorites() {
        _uiState.value = Loading

        viewModelScope.launch {
            try {
                val favorites = ravelryUserDataRepository.fetchFavoritesList()

                _uiState.value = if (favorites != null) Success(favorites) else Error
            } catch (e: Exception) {
                Log.e("FavoritesScreenVM", "Couldn't load favorites", e)
                _uiState.value = Error
            }
        }
    }
}

sealed interface FavoritesScreenUiState {
    data object Loading : FavoritesScreenUiState
    data class Success(val favorites: FavoritesListPaginated) : FavoritesScreenUiState
    data object Error : FavoritesScreenUiState
}
