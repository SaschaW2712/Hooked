package com.saschaw.hooked

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.net.toUri
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.saschaw.hooked.core.designsystem.theme.HookedTheme
import com.saschaw.hooked.ui.HookedApp
import com.saschaw.hooked.ui.rememberHookedAppState
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.ClientAuthentication
import net.openid.appauth.ClientSecretBasic
import net.openid.appauth.RedirectUriReceiverActivity
import net.openid.appauth.ResponseTypeValues


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        val serviceConfiguration = AuthorizationServiceConfiguration(
                Uri.parse(AuthConfig.AUTH_URI),
                Uri.parse(AuthConfig.TOKEN_URI)
            )

        fun getAuthRequest(): AuthorizationRequest {
            return AuthorizationRequest.Builder(
                serviceConfiguration,
                AuthConfig.CLIENT_ID,
                AuthConfig.RESPONSE_TYPE,
                Uri.parse(AuthConfig.CALLBACK_URL)
            )
                .setScope(AuthConfig.SCOPE)
                .build()
        }

        val customTabsIntent = CustomTabsIntent.Builder().build()

        val authService = AuthorizationService(application)

        val openAuthPageIntent = authService.getAuthorizationRequestIntent(
            getAuthRequest(),
            customTabsIntent
        )

        val getAuthResponse = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val dataIntent = result.data
            dataIntent?.let {
                val thing = it
                // Do stuff
            }
        }

        getAuthResponse.launch(openAuthPageIntent)

        setContent {
            enableEdgeToEdge()

            val appState = rememberHookedAppState()

            HookedTheme {
                HookedApp(appState)
            }
        }
    }
}

object AuthConfig {
    const val AUTH_URI = "https://www.ravelry.com/oauth2/auth"
    const val TOKEN_URI = "https://www.ravelry.com/oauth2/token"
    const val RESPONSE_TYPE = ResponseTypeValues.CODE
    const val SCOPE = "profile-only"
    const val CLIENT_ID = "046df0194f6c9a9d82c4762407f57f5a"
    const val CLIENT_SECRET = "QFSZe8HF_YW5Bn7yNnrXWf9p//DR_6AvMW_5S8k9"
    const val CALLBACK_URL = "com.saschaw.hooked:/oauth2redirect/ravelry"
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
fun AppAndroid_Preview() {
    HookedApp(rememberHookedAppState())
}