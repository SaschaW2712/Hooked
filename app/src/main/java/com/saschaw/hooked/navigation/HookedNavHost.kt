package com.saschaw.hooked.navigation

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.navigation.ModalBottomSheetLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.saschaw.hooked.feature.discover.navigation.DiscoverRoute
import com.saschaw.hooked.feature.discover.navigation.discoverScreen
import com.saschaw.hooked.feature.favorites.navigation.favoritesScreen
import com.saschaw.hooked.feature.patterndetails.navigation.navigateToPatternDetails
import com.saschaw.hooked.feature.patterndetails.navigation.patternDetailsScreen
import com.saschaw.hooked.ui.HookedAppState

@Suppress("ktlint:standard:function-naming")
@Composable
fun HookedNavHost(
    appState: HookedAppState,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
) {
    val navController = appState.navController
    ModalBottomSheetLayout(appState.bottomSheetNavigator) {
        NavHost(
            navController = navController,
            startDestination = DiscoverRoute,
            modifier = modifier,
        ) {
            discoverScreen(onPatternClick = navController::navigateToPatternDetails)

            patternDetailsScreen(onDismiss = navController::popBackStack)

            favoritesScreen(onPatternClick = navController::navigateToPatternDetails)
        }
    }
}
