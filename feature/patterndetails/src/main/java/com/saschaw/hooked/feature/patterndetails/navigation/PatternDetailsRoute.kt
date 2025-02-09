package com.saschaw.hooked.feature.patterndetails.navigation

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.navigation.bottomSheet
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import com.saschaw.hooked.feature.patterndetails.PatternDetailsScreen

fun NavController.navigateToPatternDetails(
    patternId: Int,
    navOptions: NavOptions? = null
) {
    navigate("patternDetails/${patternId}", navOptions)
}

fun NavGraphBuilder.patternDetailsScreen(onDismiss: () -> Unit) {
    // The `bottomSheet` extension doesn't yet support serializable routes
    bottomSheet("patternDetails/{patternId}") { _ ->
        PatternDetailsScreen(onDismiss = onDismiss)
    }
}