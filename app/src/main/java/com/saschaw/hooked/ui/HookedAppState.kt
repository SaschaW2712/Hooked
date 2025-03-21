package com.saschaw.hooked.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.saschaw.hooked.core.datastore.PreferencesDataSource
import com.saschaw.hooked.feature.discover.navigation.navigateToDiscover
import com.saschaw.hooked.feature.favorites.navigation.navigateToFavorites
import com.saschaw.hooked.navigation.TopLevelDestination
import com.saschaw.hooked.navigation.TopLevelDestination.DISCOVER
import com.saschaw.hooked.navigation.TopLevelDestination.FAVORITES
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.navigation.BottomSheetNavigator
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.navigation.rememberBottomSheetNavigator

@Composable
fun rememberHookedAppState(
    preferences: PreferencesDataSource,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    bottomSheetNavigator: BottomSheetNavigator = rememberBottomSheetNavigator(),
    navController: NavHostController = rememberNavController(bottomSheetNavigator),
): HookedAppState =
    remember(
        preferences,
        bottomSheetNavigator,
        navController,
        coroutineScope,
    ) {
        HookedAppState(
            preferences = preferences,
            navController = navController,
            bottomSheetNavigator = bottomSheetNavigator
        )
    }

@Stable
class HookedAppState(
    val preferences: PreferencesDataSource,
    val bottomSheetNavigator: BottomSheetNavigator,
    val navController: NavHostController,
) {
    val currentDestination: NavDestination?
        @Composable get() =
            navController
                .currentBackStackEntryAsState()
                .value
                ?.destination

    /**
     * Map of top level destinations to be used in the TopBar, BottomBar and NavRail. The key is the
     * route.
     */
    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries

    fun navigateToTopLevelDestination(destination: TopLevelDestination) {
        val options =
            navOptions {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }

        when (destination) {
            DISCOVER -> navController.navigateToDiscover(options)
            FAVORITES -> navController.navigateToFavorites(options)
        }
    }

    fun getOnboardingState(): Flow<OnboardingState> =
        preferences.getHasSeenOnboarding().combine(preferences.getAuthState()) { hasSeenOnboarding, authState ->
            return@combine when {
                !hasSeenOnboarding -> OnboardingState.ShowOnboarding()
                authState == null || !authState.isAuthorized || authState.refreshToken == null -> {
                    val loggedOutMessage =
                        "You have been logged out. Please connect your account again to continue."
                    OnboardingState.ShowOnboarding(loggedOutMessage)
                }

                else -> OnboardingState.HideOnboarding
            }

        }
}

sealed class OnboardingState {
    data object HideOnboarding: OnboardingState()
    data class ShowOnboarding(val message: String? = null): OnboardingState()
}