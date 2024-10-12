package com.saschaw.hooked.feature.favorites

import HookedButton
import HookedButtonStyle
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Settings
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
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.saschaw.hooked.core.model.FavoritesListPaginated

@OptIn(ExperimentalMaterial3Api::class)
@Suppress("ktlint:standard:function-naming")
@Composable
fun FavoritesScreen(
    viewModel: FavoritesScreenViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    val onRefresh = {}

    Scaffold(
        topBar = {
            TopAppBar({ Text("Favorites")}, actions = {
                IconButton(onClick = onRefresh) {
                    Icon(Icons.Rounded.Refresh, contentDescription = "Refresh")
                }
            })
        }
    ) { padding ->
        val contentModifier = Modifier.padding(padding)

        when (val uiStateValue = uiState.value) {
            is FavoritesScreenUiState.Error -> {
                FavoritesScreenErrorContent(contentModifier, onRefresh)
            }
            FavoritesScreenUiState.Loading -> {
                Box(contentModifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is FavoritesScreenUiState.Success -> {
                if (uiStateValue.favorites == null) {
                    FavoritesScreenErrorContent(contentModifier, onRefresh)
                } else {
                    FavoritesScreenSuccessContent(contentModifier, uiStateValue.favorites)
                }
            }
        }
    }
}

@Composable
fun FavoritesScreenErrorContent(
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit
) {
    Column(modifier) {
        Text("Something went wrong. Please try refreshing, or restart your app!")

        HookedButton(
            onClick = onRefresh,
            style = HookedButtonStyle.Primary,
            text = { Text("Try again") }
        )
    }

}

@Composable
fun FavoritesScreenSuccessContent(
    modifier: Modifier = Modifier,
    favoritesPaginated: FavoritesListPaginated,
) {
    val currentPageFavorites = favoritesPaginated.favorites.map { it.favorited }
    LazyColumn(
        modifier,
        contentPadding = PaddingValues(horizontal = 32.dp),
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
                    Image(
                        painter = painterResource(id = com.saschaw.hooked.core.designsystem.R.drawable.app_logo),
                        modifier = Modifier.fillMaxWidth(),
                        contentDescription = null
                    )

                    Column(Modifier.padding(12.dp).fillMaxWidth()) {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Image(
                                painter = painterResource(com.saschaw.hooked.core.designsystem.R.drawable.app_logo),
                                modifier = Modifier.padding(top = 2.dp).size(32.dp).clip(CircleShape),
                                contentDescription = null
                            )
                            Column {
                                Text(it.name, style = MaterialTheme.typography.titleMedium)
                                Text(it.patternAuthor.name, style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }
            }
        }
    }
}