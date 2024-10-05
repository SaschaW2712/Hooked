

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.saschaw.hooked.core.designsystem.HookedIcons
import com.saschaw.hooked.core.designsystem.theme.HookedTheme

enum class HookedButtonStyle {
    Primary,
    Secondary,
    Tertiary,
    Outlined,
    Text
}

@Composable
fun HookedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: HookedButtonStyle = HookedButtonStyle.Primary,
    enabled: Boolean = true,
    text: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    val contentPadding = if (leadingIcon != null) {
        ButtonDefaults.ButtonWithIconContentPadding
    } else {
        ButtonDefaults.ContentPadding
    }

    val content = @Composable {
        HookedButtonContent(
            text = text,
            leadingIcon = leadingIcon,
        )
    }

    when (style) {
        HookedButtonStyle.Primary -> {
            HookedButton(
                onClick = onClick,
                modifier = modifier,
                enabled = enabled,
                contentPadding = contentPadding,
            ) {
                content()
            }
        }
        HookedButtonStyle.Outlined -> {
            HookedOutlinedButton(
                onClick = onClick,
                modifier = modifier,
                enabled = enabled,
                contentPadding = contentPadding
            ) {
                content()
            }
        }
        HookedButtonStyle.Text -> {
            HookedTextButton(onClick, modifier, enabled) {
                content()
            }
        }
        HookedButtonStyle.Secondary -> {
            HookedButton(
                onClick = onClick,
                modifier = modifier,
                enabled = enabled,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                ),
                borderColor = MaterialTheme.colorScheme.onSecondaryContainer,
                contentPadding = contentPadding,
            ) {
                content()
            }
        }
        HookedButtonStyle.Tertiary -> {
            HookedButton(
                onClick = onClick,
                modifier = modifier,
                enabled = enabled,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                ),
                borderColor = MaterialTheme.colorScheme.onTertiaryContainer,
                contentPadding = contentPadding,
            ) {
                content()
            }
        }
    }
}


@Composable
private fun HookedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
    ),
    borderColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.0.dp),
        colors = colors,
        contentPadding = contentPadding,
        content = content,
        border = BorderStroke(
            width = HookedButtonDefaults.OutlinedButtonBorderWidth,
            color = if (enabled) {
                borderColor
            } else {
                MaterialTheme.colorScheme.onSurface.copy(
                    alpha = HookedButtonDefaults.DISABLED_OUTLINED_BUTTON_BORDER_ALPHA,
                )
            },
        ),
    )
}

@Composable
fun HookedOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.onBackground,
        ),
        border = BorderStroke(
            width = HookedButtonDefaults.OutlinedButtonBorderWidth,
            color = if (enabled) {
                MaterialTheme.colorScheme.outline
            } else {
                MaterialTheme.colorScheme.onSurface.copy(
                    alpha = HookedButtonDefaults.DISABLED_OUTLINED_BUTTON_BORDER_ALPHA,
                )
            },
        ),
        contentPadding = contentPadding,
        content = content,
    )
}

@Composable
fun HookedTextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit,
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.textButtonColors(
            contentColor = MaterialTheme.colorScheme.onBackground,
        ),
        content = content,
    )
}

@Composable
private fun HookedButtonContent(
    text: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    if (leadingIcon != null) {
        Box(Modifier.sizeIn(maxHeight = ButtonDefaults.IconSize)) {
            leadingIcon()
        }
    }
    Box(
        Modifier
            .padding(
                start = if (leadingIcon != null) {
                    ButtonDefaults.IconSpacing
                } else {
                    0.dp
                },
            ),
    ) {
        text()
    }
}

@Preview
@Composable
fun HookedButtonPreview() {
    HookedTheme {
        Surface(modifier = Modifier.size(150.dp, 50.dp)) {
            HookedButton(onClick = {}, text = { Text("Test button") })
        }
    }
}

@Preview
@Composable
fun HookedSecondaryButtonPreview() {
    HookedTheme {
        Surface(modifier = Modifier.size(150.dp, 50.dp)) {
            HookedButton(onClick = {}, style = HookedButtonStyle.Secondary, text = { Text("Test button") })
        }
    }
}

@Preview
@Composable
fun HookedTertiaryButtonPreview() {
    HookedTheme {
        Surface(modifier = Modifier.size(150.dp, 50.dp)) {
            HookedButton(onClick = {}, style = HookedButtonStyle.Tertiary, text = { Text("Test button") })
        }
    }
}

@Preview
@Composable
fun HookedOutlinedButtonPreview() {
    HookedTheme {
        Surface(modifier = Modifier.size(150.dp, 50.dp)) {
            HookedButton(onClick = {}, style = HookedButtonStyle.Outlined, text = { Text("Test button") })
        }
    }
}


@Preview
@Composable
fun HookedTextButtonPreview() {
    HookedTheme {
        Surface(modifier = Modifier.size(150.dp, 50.dp)) {
            HookedButton(onClick = {}, style = HookedButtonStyle.Text, text = { Text("Test button") })
        }
    }
}

@Preview
@Composable
fun HookedButtonLeadingIconPreview() {
    HookedTheme {
        Surface(modifier = Modifier.size(150.dp, 50.dp)) {
            HookedButton(
                onClick = {},
                text = { Text("Test button") },
                leadingIcon = { Icon(imageVector = HookedIcons.Add, contentDescription = null) },
            )
        }
    }
}

/**
 * Now in Android button default values.
 */
object HookedButtonDefaults {
    // TODO: File bug
    // OutlinedButton border color doesn't respect disabled state by default
    const val DISABLED_OUTLINED_BUTTON_BORDER_ALPHA = 0.12f

    // TODO: File bug
    // OutlinedButton default border width isn't exposed via ButtonDefaults
    val OutlinedButtonBorderWidth = 1.dp
}
