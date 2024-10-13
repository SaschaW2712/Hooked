package com.saschaw.hooked.core.designsystem.navigation

import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.only
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteItemColors
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Suppress("ktlint:standard:function-naming")
/**
 * Now in Android navigation suite scaffold with item and content slots.
 * Wraps Material 3 [NavigationSuiteScaffold].
 *
 * @param modifier Modifier to be applied to the navigation suite scaffold.
 * @param navigationSuiteItems A slot to display multiple items via [HookedNavigationSuiteScope].
 * @param windowAdaptiveInfo The window adaptive info.
 * @param content The app content inside the scaffold.
 */
@Composable
fun HookedNavigationSuiteScaffold(
    navigationSuiteItems: HookedNavigationSuiteScope.() -> Unit,
    modifier: Modifier = Modifier,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
    content: @Composable () -> Unit,
) {
    val layoutType =
        NavigationSuiteScaffoldDefaults
            .calculateFromAdaptiveInfo(windowAdaptiveInfo)
    val navigationSuiteItemColors =
        NavigationSuiteItemColors(
            navigationBarItemColors = NavigationBarItemDefaults.colors(
                    selectedIconColor = HookedNavigationDefaults.navigationSelectedItemColor(),
                    unselectedIconColor = HookedNavigationDefaults.navigationContentColor(),
                    selectedTextColor = HookedNavigationDefaults.navigationSelectedItemColor(),
                    unselectedTextColor = HookedNavigationDefaults.navigationContentColor(),
                    indicatorColor = HookedNavigationDefaults.navigationIndicatorColor(),
                ),
            navigationRailItemColors = NavigationRailItemDefaults.colors(
                    selectedIconColor = HookedNavigationDefaults.navigationSelectedItemColor(),
                    unselectedIconColor = HookedNavigationDefaults.navigationContentColor(),
                    selectedTextColor = HookedNavigationDefaults.navigationSelectedItemColor(),
                    unselectedTextColor = HookedNavigationDefaults.navigationContentColor(),
                    indicatorColor = HookedNavigationDefaults.navigationIndicatorColor(),
                ),
            navigationDrawerItemColors = NavigationDrawerItemDefaults.colors(
                    selectedIconColor = HookedNavigationDefaults.navigationSelectedItemColor(),
                    unselectedIconColor = HookedNavigationDefaults.navigationContentColor(),
                    selectedTextColor = HookedNavigationDefaults.navigationSelectedItemColor(),
                    unselectedTextColor = HookedNavigationDefaults.navigationContentColor(),
                ),
        )

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            HookedNavigationSuiteScope(
                navigationSuiteScope = this,
                navigationSuiteItemColors = navigationSuiteItemColors,
            ).run(navigationSuiteItems)
        },
        layoutType = layoutType,
        containerColor = Color.Transparent,
        navigationSuiteColors = NavigationSuiteDefaults.colors(
                navigationBarContentColor = HookedNavigationDefaults.navigationContentColor(),
                navigationRailContainerColor = Color.Transparent,
            ),
        modifier = Modifier.consumeWindowInsets(
            NavigationBarDefaults.windowInsets.only(WindowInsetsSides.Bottom)
        )
    ) {
        content()
    }
}
