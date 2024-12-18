package com.saschaw.hooked.navigation

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
    NavHost(
        navController = navController,
        startDestination = DiscoverRoute,
        modifier = modifier,
    ) {
        discoverScreen(onPatternClick = navController::navigateToPatternDetails)

        // TODO HKD-30: Fix blank screen behind bottomsheet
        patternDetailsScreen {
            navController.popBackStack()
        }

        favoritesScreen()
    }
}
