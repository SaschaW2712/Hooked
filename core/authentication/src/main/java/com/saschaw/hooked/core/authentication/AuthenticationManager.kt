package com.saschaw.hooked.core.authentication

import android.content.Intent
import android.net.Uri
import androidx.activity.result.ActivityResult
import androidx.browser.customtabs.CustomTabsIntent
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.ClientAuthentication
import net.openid.appauth.ClientSecretBasic
import net.openid.appauth.ResponseTypeValues
import net.openid.appauth.TokenRequest
import javax.inject.Inject

interface AuthenticationManager {
    fun getAuthorizationIntent(additionalScopes: List<RavelryAuthenticationScope> = emptyList()): Intent
    fun onAuthorizationResult(result: ActivityResult)
}

enum class RavelryAuthenticationScope(name: String) {
    Offline("offline"),
    ForumWrite("forum-write"),
    MessageWrite("message-write"),
    PatternStoreRead("patternstore-read"),
    PatternStorePdf("patternstore-pdf"),
    DeliveriesRead("deliveries-read"),
    LibraryPdf("library-pdf"),
    ProfileOnly("profile-only"),
    CartsOnly("carts-only"),
}

class RavelryAuthenticationManager @Inject constructor(): AuthenticationManager {
    @Inject lateinit var authService: AuthorizationService

    private val authServiceConfig = AuthorizationServiceConfiguration(
        Uri.parse(AuthConfig.AUTH_URI),
        Uri.parse(AuthConfig.TOKEN_URI)
    )

    private val clientId = AuthConfig.CLIENT_ID
    private val clientSecret = AuthConfig.CLIENT_SECRET
    private val appCallbackUri = AuthConfig.CALLBACK_URL

    override fun getAuthorizationIntent(additionalScopes: List<RavelryAuthenticationScope>): Intent {
        val authRequest = buildAuthRequest(additionalScopes)

        return authService.getAuthorizationRequestIntent(
            authRequest,
            CustomTabsIntent.Builder().build()
        )
    }

    override fun onAuthorizationResult(result: ActivityResult) {
        result.data?.let { data ->
            val exception = AuthorizationException.fromIntent(data)?.let {
                // Handle exception
            }

            AuthorizationResponse.fromIntent(data)
                ?.createTokenExchangeRequest()
                ?.let {
                    val clientAuthentication: ClientAuthentication = ClientSecretBasic(clientSecret)

                    performTokenRequest(
                        authService,
                        clientAuthentication,
                        it,
                        onComplete = {
                            // Do stuff
                        },
                        onError = {
                            // Do stuff
                        }
                    )
                }
        }
    }

    private fun buildAuthRequest(additionalScopes: List<RavelryAuthenticationScope>?): AuthorizationRequest {
        val authRequestBuilder = AuthorizationRequest.Builder(
            authServiceConfig,
            clientId,
            ResponseTypeValues.CODE,
            Uri.parse(appCallbackUri)
        )

        if (!additionalScopes.isNullOrEmpty()) {
            authRequestBuilder.setScope(additionalScopes.joinToString(" "))
        }

        return authRequestBuilder.build()
    }

    private fun performTokenRequest(
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
}

