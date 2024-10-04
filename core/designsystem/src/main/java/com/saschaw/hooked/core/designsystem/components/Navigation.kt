package com.saschaw.hooked.core.designsystem.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.ExperimentalMaterial3AdaptiveNavigationSuiteApi
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteItemColors
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.saschaw.hooked.core.designsystem.HookedIcons
import com.saschaw.hooked.core.designsystem.theme.HookedTheme

@Suppress("ktlint:standard:function-naming")
/**
 * Now in Android navigation bar item with icon and label content slots. Wraps Material 3
 * [NavigationBarItem].
 *
 * @param selected Whether this item is selected.
 * @param onClick The callback to be invoked when this item is selected.
 * @param icon The item icon content.
 * @param modifier Modifier to be applied to this item.
 * @param selectedIcon The item icon content when selected.
 * @param enabled controls the enabled state of this item. When `false`, this item will not be
 * clickable and will appear disabled to accessibility services.
 * @param label The item text label content.
 * @param alwaysShowLabel Whether to always show the label for this item. If false, the label will
 * only be shown when this item is selected.
 */
@Composable
fun RowScope.HookedNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    alwaysShowLabel: Boolean = true,
    icon: @Composable () -> Unit,
    selectedIcon: @Composable () -> Unit = icon,
    label: @Composable (() -> Unit)? = null,
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = if (selected) selectedIcon else icon,
        modifier = modifier,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
        colors =
            NavigationBarItemDefaults.colors(
                selectedIconColor = HookedNavigationDefaults.navigationSelectedItemColor(),
                unselectedIconColor = HookedNavigationDefaults.navigationContentColor(),
                selectedTextColor = HookedNavigationDefaults.navigationSelectedItemColor(),
                unselectedTextColor = HookedNavigationDefaults.navigationContentColor(),
                indicatorColor = HookedNavigationDefaults.navigationIndicatorColor(),
            ),
    )
}

@Suppress("ktlint:standard:function-naming")
/**
 * Now in Android navigation bar with content slot. Wraps Material 3 [NavigationBar].
 *
 * @param modifier Modifier to be applied to the navigation bar.
 * @param content Destinations inside the navigation bar. This should contain multiple
 * [NavigationBarItem]s.
 */
@Composable
fun HookedNavigationBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    NavigationBar(
        modifier = modifier,
        contentColor = HookedNavigationDefaults.navigationContentColor(),
        tonalElevation = 0.dp,
        content = content,
    )
}

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
@OptIn(
    ExperimentalMaterial3AdaptiveNavigationSuiteApi::class,
    ExperimentalMaterial3AdaptiveApi::class,
)
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
            navigationBarItemColors =
                NavigationBarItemDefaults.colors(
                    selectedIconColor = HookedNavigationDefaults.navigationSelectedItemColor(),
                    unselectedIconColor = HookedNavigationDefaults.navigationContentColor(),
                    selectedTextColor = HookedNavigationDefaults.navigationSelectedItemColor(),
                    unselectedTextColor = HookedNavigationDefaults.navigationContentColor(),
                    indicatorColor = HookedNavigationDefaults.navigationIndicatorColor(),
                ),
            navigationRailItemColors =
                NavigationRailItemDefaults.colors(
                    selectedIconColor = HookedNavigationDefaults.navigationSelectedItemColor(),
                    unselectedIconColor = HookedNavigationDefaults.navigationContentColor(),
                    selectedTextColor = HookedNavigationDefaults.navigationSelectedItemColor(),
                    unselectedTextColor = HookedNavigationDefaults.navigationContentColor(),
                    indicatorColor = HookedNavigationDefaults.navigationIndicatorColor(),
                ),
            navigationDrawerItemColors =
                NavigationDrawerItemDefaults.colors(
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
        navigationSuiteColors =
            NavigationSuiteDefaults.colors(
                navigationBarContentColor = HookedNavigationDefaults.navigationContentColor(),
                navigationRailContainerColor = Color.Transparent,
            ),
        modifier = modifier,
    ) {
        content()
    }
}

/**
 * A wrapper around [NavigationSuiteScope] to declare navigation items.
 */
@OptIn(ExperimentalMaterial3AdaptiveNavigationSuiteApi::class)
class HookedNavigationSuiteScope internal constructor(
    private val navigationSuiteScope: NavigationSuiteScope,
    private val navigationSuiteItemColors: NavigationSuiteItemColors,
) {
    fun item(
        selected: Boolean,
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        icon: @Composable () -> Unit,
        selectedIcon: @Composable () -> Unit = icon,
        label: @Composable (() -> Unit)? = null,
    ) = navigationSuiteScope.item(
        selected = selected,
        onClick = onClick,
        icon = {
            if (selected) {
                selectedIcon()
            } else {
                icon()
            }
        },
        label = label,
        colors = navigationSuiteItemColors,
        modifier = modifier,
    )
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
fun HookedNavigationBarPreview() {
    val items = listOf("For you", "Saved", "Interests")
    val icons =
        listOf(
            HookedIcons.BrowseOutlined,
        )
    val selectedIcons =
        listOf(
            HookedIcons.Browse,
        )

    HookedTheme {
        HookedNavigationBar {
            items.forEachIndexed { index, item ->
                HookedNavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = icons[index],
                            contentDescription = item,
                        )
                    },
                    selectedIcon = {
                        Icon(
                            imageVector = selectedIcons[index],
                            contentDescription = item,
                        )
                    },
                    label = { Text(item) },
                    selected = index == 0,
                    onClick = { },
                )
            }
        }
    }
}

/**
 * Now in Android navigation default values.
 */
object HookedNavigationDefaults {
    @Composable
    fun navigationContentColor() = MaterialTheme.colorScheme.onSurfaceVariant

    @Composable
    fun navigationSelectedItemColor() = MaterialTheme.colorScheme.onPrimaryContainer

    @Composable
    fun navigationIndicatorColor() = MaterialTheme.colorScheme.primaryContainer
}
