package com.saschaw.hooked.feature.browse.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.saschaw.hooked.feature.browse.BrowseScreen
import kotlinx.serialization.Serializable

@Serializable data object BrowseRoute

fun NavController.navigateToBrowse(navOptions: NavOptions) = navigate(route = BrowseRoute, navOptions)

fun NavGraphBuilder.browseScreen() {
    composable<BrowseRoute> {
        BrowseScreen()
    }
}
