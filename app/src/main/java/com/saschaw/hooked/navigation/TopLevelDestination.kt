package com.saschaw.hooked.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.saschaw.hooked.R
import com.saschaw.hooked.core.designsystem.HookedIcons
import com.saschaw.hooked.feature.browse.nav.BrowseRoute
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
//    BOOKMARKS(
//        selectedIcon = NiaIcons.Bookmarks,
//        unselectedIcon = NiaIcons.BookmarksBorder,
//        iconTextId = bookmarksR.string.feature_bookmarks_title,
//        titleTextId = bookmarksR.string.feature_bookmarks_title,
//        route = BookmarksRoute::class,
//    ),
//    INTERESTS(
//        selectedIcon = NiaIcons.Grid3x3,
//        unselectedIcon = NiaIcons.Grid3x3,
//        iconTextId = searchR.string.feature_search_interests,
//        titleTextId = searchR.string.feature_search_interests,
//        route = InterestsRoute::class,
//    ),
}
