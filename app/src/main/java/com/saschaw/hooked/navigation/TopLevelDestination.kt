package com.saschaw.hooked.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.saschaw.hooked.R
import com.saschaw.hooked.core.designsystem.HookedIcons
import com.saschaw.hooked.feature.browse.navigation.BrowseRoute
import com.saschaw.hooked.feature.favorites.navigation.FavoritesRoute
import kotlin.reflect.KClass

enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    @StringRes val iconTextId: Int,
    @StringRes val titleTextId: Int,
    val route: KClass<*>,
) {
    BROWSE(
        selectedIcon = HookedIcons.Browse,
        unselectedIcon = HookedIcons.BrowseOutlined,
        iconTextId = R.string.browse_icon_desc,
        titleTextId = R.string.browse_label,
        route = BrowseRoute::class,
    ),
    FAVORITES(
        selectedIcon = HookedIcons.Browse,
        unselectedIcon = HookedIcons.BrowseOutlined,
        iconTextId = R.string.favorites_icon_desc,
        titleTextId = R.string.favorites_label,
        route = FavoritesRoute::class,
    ),
}
