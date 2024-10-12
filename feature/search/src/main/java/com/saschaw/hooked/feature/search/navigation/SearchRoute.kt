package com.saschaw.hooked.feature.search.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.saschaw.hooked.feature.search.SearchScreen
import kotlinx.serialization.Serializable

@Serializable data object SearchRoute

fun NavController.navigateToSearch(navOptions: NavOptions) = navigate(route = SearchRoute, navOptions)

fun NavGraphBuilder.searchScreen() {
    composable<SearchRoute> {
        SearchScreen()
    }
}
