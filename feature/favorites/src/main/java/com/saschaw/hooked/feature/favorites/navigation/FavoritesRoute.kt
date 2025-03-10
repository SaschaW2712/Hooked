package com.saschaw.hooked.feature.favorites.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.saschaw.hooked.feature.favorites.FavoritesScreen
import kotlinx.serialization.Serializable

@Serializable
data object FavoritesRoute

fun NavController.navigateToFavorites(navOptions: NavOptions) = navigate(route = FavoritesRoute, navOptions)

fun NavGraphBuilder.favoritesScreen(onPatternClick: (Int) -> Unit) {
    composable<FavoritesRoute> {
        FavoritesScreen(onPatternClick = onPatternClick)
    }
}
