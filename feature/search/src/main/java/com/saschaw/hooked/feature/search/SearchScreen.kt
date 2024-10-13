package com.saschaw.hooked.feature.search

import HookedButton
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.saschaw.hooked.core.designsystem.theme.HookedTheme
import com.saschaw.hooked.core.model.PatternListItem
import com.saschaw.hooked.feature.search.SearchScreenUiState.Error
import com.saschaw.hooked.feature.search.SearchScreenUiState.Success

@OptIn(ExperimentalMaterial3Api::class)
@Suppress("ktlint:standard:function-naming")
@Composable
fun SearchScreen(
    viewModel: SearchScreenViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
//    val onSearch: (String) -> Unit = { query -> viewModel.onSearch(query) }

    val searchInput = remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            Column {
                TopAppBar({ Text("Search") })

                Row {
                    TextField(
                        value = searchInput.value,
                        onValueChange = { searchInput.value = it },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(0.6f)
                    )

                    HookedButton(
                        onClick = { viewModel.onSearch(searchInput.value) },
                        text = { Text("Search") }
                    )
                }
            }
        }

    ) { padding ->
        val contentModifier = Modifier.padding(padding)

        val enterAnimation = fadeIn()
        val exitAnimation = fadeOut()

        AnimatedVisibility(
            visible = uiState == SearchScreenUiState.Loading,
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
            SearchScreenErrorContent(contentModifier)
        }

        AnimatedVisibility(
            visible = uiState is Success,
            enter = enterAnimation,
            exit = exitAnimation
        ) {
            val patterns = (uiState as? Success)?.results?.patterns

            // Empty list case is impossible in practice unless actively transitioning
            SearchScreenSuccessContent(
                contentModifier,
                patterns ?: emptyList()
            )
        }
    }
}

@Composable
fun SearchScreenErrorContent(
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Text("uh oh")
    }
}

@Composable
fun SearchScreenSuccessContent(
    modifier: Modifier = Modifier,
    patterns: List<PatternListItem>,
) {
    LazyColumn(modifier) {
        items(items = patterns) {
            Text(it.name)
        }
    }
}

@Preview
@Composable
private fun SearchScreenPreview() {
    HookedTheme {
        Surface {
            SearchScreen()
        }
    }
}

