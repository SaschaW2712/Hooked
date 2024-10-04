package com.saschaw.hooked.core.designsystem.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.saschaw.hooked.core.designsystem.HookedIcons
import com.saschaw.hooked.core.designsystem.theme.HookedTheme

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
