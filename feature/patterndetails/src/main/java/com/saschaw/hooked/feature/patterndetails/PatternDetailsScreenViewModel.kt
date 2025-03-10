package com.saschaw.hooked.feature.patterndetails

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saschaw.hooked.core.data.repository.RavelryFavoritesRepository
import com.saschaw.hooked.core.data.repository.RavelryPatternDetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatternDetailsScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val patternDetailsRepository: RavelryPatternDetailsRepository,
    private val favoritesRepository: RavelryFavoritesRepository,
) : ViewModel() {
    private val patternId: Int = savedStateHandle.get<String>("patternId")?.toIntOrNull() ?: -1

    private val _uiState = MutableStateFlow<PatternDetailsScreenUiState>(PatternDetailsScreenUiState.Loading)
    val uiState: StateFlow<PatternDetailsScreenUiState> = _uiState.asStateFlow()

    fun onLoadPattern() {
        _uiState.value = PatternDetailsScreenUiState.Loading

        viewModelScope.launch {
            try {
                val patternResult = patternDetailsRepository.getPatternDetails(patternId)

                _uiState.value =
                    patternResult?.let { result ->
                        val favoriteId: Int? = if (result.personalAttributes?.favorited == true) {
                            val favorites = favoritesRepository
                                .fetchFavoritesList()
                                ?.favorites

                            favorites
                                ?.firstOrNull { it.favorited.id == patternId }
                                ?.id
                        } else null

                        PatternDetailsScreenUiState.Success(
                            photoUrl = result.photos?.firstOrNull()?.mediumUrl,
                            authorName = result.patternAuthor?.name,
                            authorPhotoUrl = result.patternAuthor?.users?.firstOrNull()?.photoUrl,
                            title = result.name,
                            isFavorited = result.personalAttributes?.favorited ?: false,
                            price = result.displayPrice,
                            favoriteId = favoriteId
                        )
                    } ?: PatternDetailsScreenUiState.Error
            } catch (e: Exception) {
                Log.e("PatternDetailsScreenVM", "Couldn't load pattern details", e)
                _uiState.value = PatternDetailsScreenUiState.Error
            }
        }
    }

    fun onLikePattern() {
        viewModelScope.launch {
            try {
                (uiState.value as? PatternDetailsScreenUiState.Success)?.let { state ->
                    if (state.isFavorited && state.favoriteId != null) {
                        val result = favoritesRepository.removeFavorite(state.favoriteId)
                        result?.let {
                            _uiState.value = state.copy(isFavorited = false)
                        }
                    } else {
                        favoritesRepository.addToFavorites(patternId)?.let {
                            _uiState.value = state.copy(isFavorited = true, favoriteId = it.id)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("PatternDetailsScreenVM", "Failed to update favorites", e)
                _uiState.value = PatternDetailsScreenUiState.Error
            }
        }
    }
}

sealed interface PatternDetailsScreenUiState {
    data object Loading : PatternDetailsScreenUiState

    data class Success(
        val photoUrl: String?,
        val authorName: String?,
        val authorPhotoUrl: String?,
        val title: String,
        val isFavorited: Boolean,
        val favoriteId: Int? = null,
        val price: String
    ) : PatternDetailsScreenUiState

    data object Error : PatternDetailsScreenUiState
}