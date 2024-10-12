package com.saschaw.hooked.feature.favorites

import HookedButton
import HookedButtonStyle
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.saschaw.hooked.core.model.FavoritesListItem
import com.saschaw.hooked.feature.favorites.FavoritesScreenUiState.Error
import com.saschaw.hooked.feature.favorites.FavoritesScreenUiState.Loading
import com.saschaw.hooked.feature.favorites.FavoritesScreenUiState.Success

@OptIn(ExperimentalMaterial3Api::class)
@Suppress("ktlint:standard:function-naming")
@Composable
fun FavoritesScreen(
    viewModel: FavoritesScreenViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    val onRefresh = { viewModel.refreshFavorites() }

    Scaffold(
        topBar = {
            TopAppBar({ Text("Favorites") }, actions = {
                IconButton(onClick = onRefresh) {
                    Icon(Icons.Rounded.Refresh, contentDescription = "Refresh")
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
                CircularProgressIndicator()
            }
        }

        AnimatedVisibility(
            visible = uiState == Error,
            enter = enterAnimation,
            exit = exitAnimation
        ) {
            FavoritesScreenErrorContent(contentModifier, onRefresh)
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
                favorites ?: emptyList()
            )
        }
    }
}

@Composable
fun FavoritesScreenErrorContent(
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit
) {
    Column(modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text(stringResource(R.string.error_title), style = MaterialTheme.typography.titleLarge)
        Text(stringResource(R.string.error_body))

        Spacer(Modifier.height(16.dp))

        HookedButton(
            onClick = onRefresh,
            style = HookedButtonStyle.Primary,
            text = { Text(stringResource(R.string.error_button_text), style = MaterialTheme.typography.bodyLarge) }
        )
    }

}

@Composable
fun FavoritesScreenSuccessContent(
    modifier: Modifier = Modifier,
    favoritesList: List<FavoritesListItem>,
) {
    val currentPageFavorites = favoritesList.map { it.favorited }
    LazyColumn(
        modifier,
        contentPadding = PaddingValues(horizontal = 32.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(currentPageFavorites) {
            Card(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer
                ),
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    // TODO: Replace with real images
                    Image(
                        painter = painterResource(id = com.saschaw.hooked.core.designsystem.R.drawable.app_logo),
                        modifier = Modifier.fillMaxWidth(),
                        contentDescription = null
                    )

                    Column(
                        Modifier
                            .padding(12.dp)
                            .fillMaxWidth()) {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            // TODO: Replace with real images
                            Image(
                                painter = painterResource(com.saschaw.hooked.core.designsystem.R.drawable.app_logo),
                                modifier = Modifier
                                    .padding(top = 2.dp)
                                    .size(40.dp)
                                    .clip(CircleShape),
                                contentDescription = null
                            )
                            Column {
                                Text(it.name, style = MaterialTheme.typography.titleMedium)

                                it.patternAuthor?.name?.let { authorName ->
                                    Text(authorName, style = MaterialTheme.typography.bodySmall)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}