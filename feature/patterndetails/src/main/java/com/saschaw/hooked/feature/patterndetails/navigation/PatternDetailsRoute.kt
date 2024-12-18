package com.saschaw.hooked.feature.patterndetails.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.saschaw.hooked.feature.patterndetails.PatternDetailsScreen
import kotlinx.serialization.Serializable

@Serializable data class PatternDetailsRoute(val patternId: Int)

fun NavController.navigateToPatternDetails(
    patternId: Int,
    navOptions: NavOptions? = null
) {
    navigate(route = PatternDetailsRoute(patternId), navOptions)
}

fun NavGraphBuilder.patternDetailsScreen(onDismiss: () -> Unit) {
    composable<PatternDetailsRoute> {
        PatternDetailsScreen(onDismiss = onDismiss)
    }
}