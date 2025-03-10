package com.saschaw.hooked.feature.favorites

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.saschaw.hooked.core.designsystem.components.PatternCard
import com.saschaw.hooked.core.model.lists.favorites.FavoritesListItem
import com.saschaw.hooked.feature.favorites.FavoritesScreenUiState.Error
import com.saschaw.hooked.feature.favorites.FavoritesScreenUiState.Loading
import com.saschaw.hooked.feature.favorites.FavoritesScreenUiState.Success

@OptIn(ExperimentalMaterial3Api::class)
@Suppress("ktlint:standard:function-naming")
@Composable
fun FavoritesScreen(
    viewModel: FavoritesScreenViewModel = hiltViewModel(),
    onPatternClick: (Int) -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    Scaffold(
        topBar = {
            TopAppBar({ Text(stringResource(R.string.favorites_feature_name), style = MaterialTheme.typography.headlineMedium) }, actions = {
                IconButton(onClick = { viewModel.refreshFavorites() }) {
                    Icon(
                        Icons.Rounded.Refresh,
                        modifier = Modifier.size(28.dp),
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = stringResource(R.string.refresh_button_content_desc)
                    )
                }
            })
        }
    ) { padding ->
        val contentModifier = Modifier.padding(padding)

        val enterAnimation = fadeIn()
        val exitAnimation = fadeOut()

        AnimatedVisibility(
            visible = uiState == Loading,
            enter = enterAnimation,
            exit = exitAnimation
        ) {
            Box(contentModifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }

        AnimatedVisibility(
            visible = uiState == Error,
            enter = enterAnimation,
            exit = exitAnimation
        ) {
            FavoritesScreenErrorContent(contentModifier)
        }

        AnimatedVisibility(
            visible = uiState is Success,
            enter = enterAnimation,
            exit = exitAnimation
        ) {
            val favorites = (uiState as? Success)?.favorites?.favorites

            // Empty list case is impossible in practice unless actively transitioning
            FavoritesScreenSuccessContent(
                contentModifier,
                favorites ?: emptyList(),
                onPatternClick
            )
        }
    }
}

@Composable
fun FavoritesScreenErrorContent(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(stringResource(R.string.error_title), style = MaterialTheme.typography.titleLarge, textAlign = Center)
        Spacer(Modifier.height(8.dp))
        Text(stringResource(R.string.error_body), textAlign = Center)
    }
}

@Composable
fun FavoritesScreenSuccessContent(
    modifier: Modifier = Modifier,
    favoritesList: List<FavoritesListItem>,
    onPatternClick: (Int) -> Unit,
) {
    val currentPageFavorites = favoritesList.map { it.favorited }
    LazyColumn(
        modifier,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(currentPageFavorites) { pattern ->
            PatternCard(Modifier.fillMaxWidth(), pattern = pattern, onClick = {
                onPatternClick(pattern.id)
            })
        }
    }
}