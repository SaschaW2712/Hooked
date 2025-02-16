package com.saschaw.hooked.feature.discover

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.saschaw.hooked.core.designsystem.HookedIcons
import com.saschaw.hooked.core.designsystem.components.PatternCard
import com.saschaw.hooked.core.designsystem.theme.HookedTheme
import com.saschaw.hooked.core.model.pattern.PatternListItem
import com.saschaw.hooked.feature.discover.DiscoverScreenUiState.Error
import com.saschaw.hooked.feature.discover.DiscoverScreenUiState.Success

@OptIn(ExperimentalMaterial3Api::class)
@Suppress("ktlint:standard:function-naming")
@Composable
fun DiscoverScreen(
    viewModel: DiscoverScreenViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val searchInput = remember { mutableStateOf("") }

    LaunchedEffect(Unit) { viewModel.getHotRightNow() }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Column(Modifier.padding(bottom = 12.dp)) {
                TopAppBar(
                    title = {
                        Text(
                            stringResource(R.string.discover_feature_title),
                            style = MaterialTheme.typography.headlineMedium
                        )
                    },
                    scrollBehavior = scrollBehavior
                )

                SearchInput(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                    value = searchInput.value,
                    onValueChanged = { searchInput.value = it },
                    onSearch = {
                        viewModel.onSearch(it)
                    }
                )
            }
        }
    ) { padding ->
        val contentModifier = Modifier.padding(padding)

        val enterAnimation = fadeIn()
        val exitAnimation = fadeOut()

        AnimatedVisibility(
            visible = uiState == DiscoverScreenUiState.Loading,
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
            DiscoverScreenErrorContent(contentModifier)
        }

        AnimatedVisibility(
            visible = uiState is Success,
            enter = enterAnimation,
            exit = exitAnimation
        ) {

            val patterns = (uiState as? Success)?.searchQueryWithResults?.results?.patterns
            val lastSearchQuery = (uiState as? Success)?.searchQueryWithResults?.query

            // Empty list case is impossible in practice unless actively transitioning
            DiscoverScreenSuccessContent(
                contentModifier,
                lastSearchQuery,
                patterns ?: emptyList()
            )
        }
    }
}

@Composable
fun SearchInput(
    modifier: Modifier = Modifier,
    value: String,
    onValueChanged: (String) -> Unit,
    onSearch: (String) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = value,
        onValueChange = { onValueChanged(it) },
        modifier = modifier
            .fillMaxWidth(),
        singleLine = true,
        placeholder = { Text("Enter a search term") },
        shape = RoundedCornerShape(8.dp),
        leadingIcon = {
            Icon(
                HookedIcons.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.outline
            )
        },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.outline,
            focusedIndicatorColor = MaterialTheme.colorScheme.outline,
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            keyboardController?.hide()
            onSearch(value)
        })
    )
}

@Composable
fun DiscoverScreenErrorContent(
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
fun DiscoverScreenSuccessContent(
    modifier: Modifier = Modifier,
    searchQuery: String?,
    patterns: List<PatternListItem>,
) {
    val uriHandler = LocalUriHandler.current
    val context = LocalContext.current

    LazyColumn(
        modifier,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            Text(
                text = if (searchQuery.isNullOrEmpty()) {
                    stringResource(R.string.hot_right_now_label)
                } else {
                    stringResource(R.string.results_for_query_label, searchQuery)
                },
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleMedium
            )
        }

        items(patterns) { pattern ->
            PatternCard(Modifier.fillMaxWidth(), pattern = pattern, onClick = {
                uriHandler.openUri(
                    context.getString(
                        R.string.ravelry_pattern_base_url,
                        pattern.permalink
                    ))
            })
        }
    }
}

@Preview
@Composable
private fun DiscoverScreenPreview() {
    HookedTheme {
        Surface {
            DiscoverScreen()
        }
    }
}

