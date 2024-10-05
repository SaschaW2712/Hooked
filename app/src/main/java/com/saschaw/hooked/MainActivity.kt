package com.saschaw.hooked

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.saschaw.hooked.core.designsystem.theme.HookedTheme
import com.saschaw.hooked.ui.HookedApp
import com.saschaw.hooked.ui.rememberHookedAppState
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.ClientAuthentication
import net.openid.appauth.ClientSecretBasic
import net.openid.appauth.ResponseTypeValues
import net.openid.appauth.TokenRequest


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        val authServiceConfig = AuthorizationServiceConfiguration(
            Uri.parse(AuthConfig.AUTH_URI),
            Uri.parse(AuthConfig.TOKEN_URI)
        )

        val authService = AuthorizationService(application)
        val clientAuthentication: ClientAuthentication = ClientSecretBasic(AuthConfig.CLIENT_SECRET)

        val authRequest = AuthorizationRequest.Builder(
            authServiceConfig,
            AuthConfig.CLIENT_ID,
            AuthConfig.RESPONSE_TYPE,
            Uri.parse(AuthConfig.CALLBACK_URL)
        )
            .setScope(AuthConfig.SCOPE)
            .build()

        val openAuthPageIntent = authService.getAuthorizationRequestIntent(
            authRequest,
            CustomTabsIntent.Builder().build()
        )

        val getAuthResponse = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            result.data?.let { data ->
                val exception = AuthorizationException.fromIntent(data)?.let {
                    // Handle exception
                }
                AuthorizationResponse.fromIntent(data)
                    ?.createTokenExchangeRequest()
                    ?.let {
                        performTokenRequest(
                            authService,
                            clientAuthentication,
                            it,
                            onComplete = {},
                            onError = {}
                        )
                    }
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


fun performTokenRequest(
    authService: AuthorizationService,
    clientAuthentication: ClientAuthentication,
    tokenRequest: TokenRequest,
    onComplete: () -> Unit,
    onError: () -> Unit
) {
    authService.performTokenRequest(tokenRequest, clientAuthentication) { response, ex ->
        when {
            response != null -> {
                val accessToken = response.accessToken.orEmpty()
                val refreshToken = response.refreshToken
                onComplete()
            }
            else -> onError()
        }
    }
}

object AuthConfig {
    const val AUTH_URI = "https://www.ravelry.com/oauth2/auth"
    const val TOKEN_URI = "https://www.ravelry.com/oauth2/token"
    const val RESPONSE_TYPE = ResponseTypeValues.CODE
    const val SCOPE = "offline"
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