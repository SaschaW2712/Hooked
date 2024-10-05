package com.saschaw.hooked.feature.browse

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.saschaw.hooked.core.designsystem.components.HookedButton
import com.saschaw.hooked.core.designsystem.components.HookedButtonStyle

@Suppress("ktlint:standard:function-naming")
@Composable
fun BrowseScreen() {
    val state = rememberScrollState()
    Column(
        Modifier.padding(horizontal = 8.dp).verticalScroll(state),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Wow, headline!", style = MaterialTheme.typography.headlineMedium)

        Column(verticalArrangement = Arrangement.spacedBy(8.dp), horizontalAlignment = Alignment.Start) {
            Text("This is a title", style = MaterialTheme.typography.titleLarge)
            Text("And a smaller subtitle", style = MaterialTheme.typography.titleSmall)
            Text(
                "Smaller body text because I'm cool and radical, and this material theme slaps I hope.",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Image(
            painterResource(com.saschaw.hooked.core.designsystem.R.drawable.app_logo),
            contentDescription = "Hooked app logo, a ball of yarn with a crochet hook placed in front of it."
        )
        Text("Hooked logo, and also a label", style = MaterialTheme.typography.labelMedium)

        Spacer(Modifier.height(24.dp))

        HookedButton(onClick = {}, text = { Text("This is a button") })
        HookedButton(
            onClick = {},
            style = HookedButtonStyle.Secondary,
            text = { Text("Secondary button") })

        HookedButton(
            onClick = {},
            style = HookedButtonStyle.Tertiary,
            text = { Text("Tertiary button") })

        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            HookedButton(
                onClick = {},
                style = HookedButtonStyle.Outlined,
                text = { Text("Outlined button...") })

            HookedButton(
                onClick = {},
                style = HookedButtonStyle.Text,
                text = { Text("...and a text button") })
        }

        Spacer(Modifier.height(24.dp))

        Text(
            "Even more body text, going way off the screen, so we can make sure this scrolls as desired if my phone's text size is large, or if there's a lot of content on the screen. This theming was a real bitch to set up, but I'm quite happy with how it turned out. I find the automatically generated Material Theme colours in general to be far too muted and dull, so I did definitely change the tertiary colour deliberately to make it a little more fun. The buttons are also butt-ugly by default, because if you want a light main colour for your app the material colour scheme makes that the primary container and turns your primary colour into an ugly darker version. All that to say, I'm using the container colours, not the primary/tertiary/secondary colours for my buttons.",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
