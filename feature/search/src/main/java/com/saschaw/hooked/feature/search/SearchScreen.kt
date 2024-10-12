package com.saschaw.hooked.feature.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.saschaw.hooked.core.designsystem.theme.HookedTheme

@Suppress("ktlint:standard:function-naming")
@Composable
fun SearchScreen(
    viewModel: SearchScreenViewModel = hiltViewModel(),
) {
    val state = rememberScrollState()

    Column(
        modifier = Modifier
            .padding(horizontal = 36.dp, vertical = 36.dp)
            .verticalScroll(state),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Search page WIP")
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

