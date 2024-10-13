package com.saschaw.hooked.feature.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.saschaw.hooked.core.designsystem.HookedIcons
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

    val searchInput = remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            Column(Modifier.padding(bottom = 12.dp)) {
                TopAppBar({ Text("Search") })

                SearchInput(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                    value = searchInput.value,
                    onValueChanged = { searchInput.value = it },
                    onSearch = { viewModel.onSearch(it) }
                )
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
fun SearchScreenErrorContent(
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
fun SearchScreenSuccessContent(
    modifier: Modifier = Modifier,
    patterns: List<PatternListItem>,
) {
    LazyColumn(
        modifier,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(patterns) { pattern ->
            Card(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer
                ),
            ) {
                val context = LocalContext.current

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(pattern.firstPhoto?.mediumUrl)
                            .crossfade(true)
                            .build(),
                        fallback = painterResource(com.saschaw.hooked.core.designsystem.R.drawable.app_logo),
                        modifier = Modifier
                            .fillMaxSize()
                            .height(200.dp),
                        contentScale = ContentScale.FillWidth,
                        contentDescription = null,
                    )

                    Column(
                        Modifier
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                            .fillMaxWidth()
                    ) {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            AsyncImage(
                                model = ImageRequest.Builder(context)
                                    .data(pattern.patternAuthor?.users?.firstOrNull()?.photoUrl)
                                    .crossfade(true)
                                    .build(),
                                fallback = painterResource(com.saschaw.hooked.core.designsystem.R.drawable.app_logo),
                                modifier = Modifier
                                    .padding(top = 4.dp)
                                    .size(40.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.FillBounds,
                                contentDescription = "Author profile image"
                            )

                            Column {
                                Text(pattern.name, style = MaterialTheme.typography.titleMedium)

                                pattern.patternAuthor?.name?.let { authorName ->
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

@Preview
@Composable
private fun SearchScreenPreview() {
    HookedTheme {
        Surface {
            SearchScreen()
        }
    }
}

