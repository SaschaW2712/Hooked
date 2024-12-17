package com.saschaw.hooked.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.saschaw.hooked.R
import com.saschaw.hooked.core.designsystem.HookedIcons
import com.saschaw.hooked.feature.discover.navigation.DiscoverRoute
import com.saschaw.hooked.feature.favorites.navigation.FavoritesRoute
import kotlin.reflect.KClass

enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    @StringRes val iconTextId: Int,
    @StringRes val titleTextId: Int,
    val route: KClass<*>,
) {
    DISCOVER(
        selectedIcon = HookedIcons.Discover,
        unselectedIcon = HookedIcons.DiscoverOutlined,
        iconTextId = R.string.discover_icon_desc,
        titleTextId = R.string.discover_label,
        route = DiscoverRoute::class,
    ),
    FAVORITES(
        selectedIcon = HookedIcons.Favorite,
        unselectedIcon = HookedIcons.FavoriteOutlined,
        iconTextId = R.string.favorites_icon_desc,
        titleTextId = R.string.favorites_label,
        route = FavoritesRoute::class,
    ),
}
