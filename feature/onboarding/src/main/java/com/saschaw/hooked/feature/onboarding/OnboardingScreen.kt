package com.saschaw.hooked.feature.onboarding

import HookedButton
import HookedButtonStyle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.saschaw.hooked.core.authentication.AuthenticationManager
import com.saschaw.hooked.core.designsystem.components.ColorStyle
import com.saschaw.hooked.core.designsystem.components.HighlightedText
import com.saschaw.hooked.core.designsystem.components.TitleWithLogo
import com.saschaw.hooked.core.designsystem.theme.HookedTheme

@Suppress("ktlint:standard:function-naming")
@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    authenticationManager: AuthenticationManager,
    onFinishOnboarding: () -> Unit,
) {
    val state = rememberScrollState()

    val authLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            authenticationManager.onAuthorizationResult(it)
            onFinishOnboarding()
        }

    Scaffold(
        modifier = modifier,
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .consumeWindowInsets(padding)
                .padding(horizontal = 36.dp, vertical = 36.dp)
                .verticalScroll(state),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TitleWithLogo(Modifier.padding(vertical = 8.dp))

            HighlightedText(ColorStyle.Primary, stringResource(R.string.update_your_current_projects))
            HighlightedText(ColorStyle.Secondary, stringResource(R.string.find_new_inspiration))
            HighlightedText(ColorStyle.Tertiary, stringResource(R.string.connect_with_the_community))

            Spacer(Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.onboarding_cta_body),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = Center,
                modifier = Modifier.padding(horizontal = 40.dp)
            )

            HookedButton(
                onClick = {
                    authLauncher.launch(authenticationManager.getAuthorizationIntent())
                },
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
                style = HookedButtonStyle.Primary,
                text = {
                    Text(
                        text = stringResource(R.string.onboarding_cta_button_label),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(8.dp),
                    )
                },
            )

            Text(
                text = stringResource(R.string.privacy_note),
                style = MaterialTheme.typography.bodySmall,
                textAlign = Center,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview
@Composable
private fun OnboardingScreenPreview() {
    HookedTheme {
        Surface {
//            OnboardingScreen(snackbarHostState = remember { SnackbarHostState() }) {}
        }
    }
}

