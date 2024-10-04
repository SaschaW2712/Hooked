package com.saschaw.hooked.core.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Suppress("ktlint:standard:function-naming")
@Composable
fun HookedTheme(content: @Composable () -> Unit) {
    MaterialTheme {
        content()
    }
}
