package com.saschaw.hooked.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalAbsoluteTonalElevation
import androidx.compose.material3.SnackbarDuration.Short
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult.ActionPerformed
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.saschaw.hooked.core.designsystem.navigation.HookedNavigationSuiteScaffold
import com.saschaw.hooked.feature.onboarding.OnboardingScreen
import com.saschaw.hooked.navigation.HookedNavHost
import kotlin.reflect.KClass

@Suppress("ktlint:standard:function-naming")
@Composable
fun HookedApp(
    appState: HookedAppState,
    modifier: Modifier = Modifier,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Surface(
        color = Color.Transparent,
        modifier = modifier.fillMaxSize(),
    ) {
        CompositionLocalProvider(LocalAbsoluteTonalElevation provides 0.dp) {
            HookedApp(
                appState = appState,
                snackbarHostState = snackbarHostState,
                modifier = modifier,
                windowAdaptiveInfo = windowAdaptiveInfo,
            )
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
internal fun HookedApp(
    appState: HookedAppState,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
) {
    val currentDestination = appState.currentDestination

    val onboardingState = appState.getOnboardingState().collectAsStateWithLifecycle(OnboardingState.ShowOnboarding())

    AnimatedVisibility(onboardingState.value != OnboardingState.HideOnboarding) {
        OnboardingScreen(modifier, snackbarHostState)
    }

    AnimatedVisibility(onboardingState.value == OnboardingState.HideOnboarding) {
        HookedNavigationSuiteScaffold(
            navigationSuiteItems = {
                appState.topLevelDestinations.forEach { destination ->
                    val selected = currentDestination.isRouteInHierarchy(destination.route)

                    item(
                        selected = selected,
                        onClick = { appState.navigateToTopLevelDestination(destination) },
                        icon = {
                            Icon(
                                imageVector = destination.unselectedIcon,
                                contentDescription = null,
                            )
                        },
                        selectedIcon = {
                            Icon(
                                imageVector = destination.selectedIcon,
                                contentDescription = null,
                            )
                        },
                        label = { Text(stringResource(destination.iconTextId)) },
                    )
                }
            },
            windowAdaptiveInfo = windowAdaptiveInfo,
        ) {
            HookedNavHost(
                appState = appState,
                onShowSnackbar = { message, action ->
                    snackbarHostState.showSnackbar(
                        message = message,
                        actionLabel = action,
                        duration = Short,
                    ) == ActionPerformed
                },
            )
        }
    }
}

private fun NavDestination?.isRouteInHierarchy(route: KClass<*>) =
    this?.hierarchy?.any {
        it.hasRoute(route)
    } ?: false
