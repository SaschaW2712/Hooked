package com.saschaw.hooked.core.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import com.saschaw.hooked.core.designsystem.R

@Composable
fun TitleWithLogo(
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.headlineLarge,
) {
    val annotatedString = buildAnnotatedString {
        append("Hooked ")
        appendInlineContent(id = "hookedLogoId")
    }
    val iconSize = textStyle.fontSize

    val inlineContentMap = mapOf(
        "hookedLogoId" to InlineTextContent(
            Placeholder(iconSize, iconSize, PlaceholderVerticalAlign.TextCenter)
        ) {
            Image(
                painterResource(R.drawable.app_logo), contentDescription = stringResource(
                R.string.app_logo_desc)
            )
        }
    )

    Text(annotatedString, inlineContent = inlineContentMap, style = textStyle, modifier = modifier)
}