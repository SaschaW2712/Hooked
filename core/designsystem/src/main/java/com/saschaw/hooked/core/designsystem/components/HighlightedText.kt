package com.saschaw.hooked.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.unit.dp
import com.saschaw.hooked.core.designsystem.components.ColorStyle.Primary
import com.saschaw.hooked.core.designsystem.components.ColorStyle.Secondary
import com.saschaw.hooked.core.designsystem.components.ColorStyle.Tertiary

enum class ColorStyle {
    Primary,
    Secondary,
    Tertiary
}

@Composable
fun HighlightedText(
    colorStyle: ColorStyle,
    text: String,
) {
    Box(
        Modifier
            .fillMaxWidth()
            .background(
                color = when (colorStyle) {
                    Primary -> MaterialTheme.colorScheme.primaryContainer
                    Secondary -> MaterialTheme.colorScheme.secondaryContainer
                    Tertiary -> MaterialTheme.colorScheme.tertiaryContainer
                },
                shape = RoundedCornerShape(4.dp),
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text,
            style = MaterialTheme.typography.titleMedium,
            textAlign = Center,
            color = when (colorStyle) {
                Primary -> MaterialTheme.colorScheme.onPrimaryContainer
                Secondary -> MaterialTheme.colorScheme.onSecondaryContainer
                Tertiary -> MaterialTheme.colorScheme.onTertiaryContainer
            },
            modifier = Modifier.padding(16.dp),
        )
    }
}