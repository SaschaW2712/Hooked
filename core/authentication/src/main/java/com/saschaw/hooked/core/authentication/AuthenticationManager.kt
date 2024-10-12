package com.saschaw.hooked.core.authentication

import android.content.Intent
import android.net.Uri
import androidx.activity.result.ActivityResult
import androidx.browser.customtabs.CustomTabsIntent
import com.saschaw.hooked.core.datastore.HookedPreferencesDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import net.openid.appauth.AuthState
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.ClientAuthentication
import net.openid.appauth.ClientSecretBasic
import net.openid.appauth.ResponseTypeValues
import net.openid.appauth.TokenRequest
import net.openid.appauth.TokenResponse
import javax.inject.Inject

interface AuthenticationManager {
    fun getAuthorizationIntent(): Intent
    fun onAuthorizationResult(result: ActivityResult)
    fun doAuthenticated(function: (String?, String?) -> Unit, onFailure: (Exception) -> Unit)
    suspend fun refreshTokensIfNeededBlocking()
    fun invalidateAuthentication()
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

class RavelryAuthenticationManager @Inject constructor() : AuthenticationManager {
    @Inject
    lateinit var authService: AuthorizationService

    @Inject
    lateinit var preferences: HookedPreferencesDataSource

    private val clientId = AuthConfig.CLIENT_ID
    private val clientSecret = AuthConfig.CLIENT_SECRET
    private val appCallbackUri = AuthConfig.CALLBACK_URL
    private val clientAuthentication: ClientAuthentication = ClientSecretBasic(clientSecret)

    override fun getAuthorizationIntent(): Intent {
        val authRequest = buildAuthRequest()

        return authService.getAuthorizationRequestIntent(
            authRequest,
            CustomTabsIntent.Builder().build()
        )
    }

    private fun buildAuthRequest(): AuthorizationRequest {
        val authState = AuthState(
            AuthorizationServiceConfiguration(
                Uri.parse(AuthConfig.AUTH_URI),
                Uri.parse(AuthConfig.TOKEN_URI)
            )
        )

        CoroutineScope(Dispatchers.IO).launch {
            preferences.updateAuthState(authState)
        }

        val authRequestBuilder = AuthorizationRequest.Builder(
            authState.authorizationServiceConfiguration
                ?: AuthorizationServiceConfiguration(
                    Uri.parse(AuthConfig.AUTH_URI),
                    Uri.parse(AuthConfig.TOKEN_URI),
                ),
            clientId,
            ResponseTypeValues.CODE,
            Uri.parse(appCallbackUri)
        )

        authRequestBuilder.setScope(AuthConfig.SCOPES)

        return authRequestBuilder.build()
    }

    override fun onAuthorizationResult(result: ActivityResult) {
        result.data?.let { data ->
            val exception = AuthorizationException.fromIntent(data)
            val response = AuthorizationResponse.fromIntent(data)

            CoroutineScope(Dispatchers.IO).launch {
                updateAuthState(response, exception)
            }

            exception?.let {
                // Handle exception
                CoroutineScope(Dispatchers.IO).launch {
                    clearAuthState()
                }
                return
            }

            try {
                response?.createTokenExchangeRequest()?.let {
                    try {
                        performTokenRequest(
                            authService,
                            clientAuthentication,
                            it
                        )
                    } catch (ex: Exception) {
                        CoroutineScope(Dispatchers.IO).launch {
                            clearAuthState()
                        }
                    }
                }
            } catch (ex: Exception) {
                CoroutineScope(Dispatchers.IO).launch {
                    clearAuthState()
                }
            }
        }
    }

    private fun performTokenRequest(
        authService: AuthorizationService,
        clientAuthentication: ClientAuthentication,
        tokenRequest: TokenRequest,
    ) {
        try {
            authService.performTokenRequest(
                tokenRequest,
                clientAuthentication
            ) { response, exception ->
                CoroutineScope(Dispatchers.IO).launch {
                    updateAuthState(response, exception)
                }
            }
        } catch (ex: Exception) {
            // Usually occurs due to authentication misconfiguration, clear auth state to be safe.
            CoroutineScope(Dispatchers.IO).launch {
                clearAuthState()
            }
        }
    }

    override fun doAuthenticated(function: (String?, String?) -> Unit, onFailure: (Exception) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            refreshTokensIfNeededBlocking()

            preferences.getAuthState().firstOrNull().let {

                // Ensure we're authenticated
                if (it != null) {
                    it.performActionWithFreshTokens(authService) { accessToken, idToken, ex ->
                        ex?.let {
                            // Usually occurs as a result of expired refresh token,
                            // so need to trigger full re-authentication
                            CoroutineScope(Dispatchers.IO).launch {
                                clearAuthState()
                                onFailure(ex)
                            }
                            return@performActionWithFreshTokens
                        }

                        function(accessToken, idToken)
                    }
                } else {
                    onFailure(Exception("No auth state found"))
                }
            }
        }
    }

    // Triggers onboarding and login prompt to show
    private suspend fun clearAuthState() {
        preferences.updateAuthState(null)
    }

    private suspend fun updateAuthState(
        authResponse: AuthorizationResponse?,
        authException: AuthorizationException?
    ) {
        val authState = preferences.getAuthState().firstOrNull() ?: AuthState(
            AuthorizationServiceConfiguration(
                Uri.parse(AuthConfig.AUTH_URI),
                Uri.parse(AuthConfig.TOKEN_URI)
            )
        )

        authState.update(authResponse, authException)
        preferences.updateAuthState(authState)
    }

    private suspend fun updateAuthState(
        tokenResponse: TokenResponse?,
        authException: AuthorizationException?
    ) {
        val authState = preferences.getAuthState().firstOrNull() ?: AuthState(
            AuthorizationServiceConfiguration(
                Uri.parse(AuthConfig.AUTH_URI),
                Uri.parse(AuthConfig.TOKEN_URI)
            )
        )

        authState.update(tokenResponse, authException)
        preferences.updateAuthState(authState)
    }

    override suspend fun refreshTokensIfNeededBlocking() {
        val authState = preferences.getAuthState().firstOrNull()

        if (authState == null || authState.refreshToken == null) {
            // No refresh token will be available, do not continue.
            return
        } else if (authState.needsTokenRefresh) {
            try {
                performTokenRequest(
                    authService,
                    clientAuthentication,
                    authState.createTokenRefreshRequest()
                )
            } catch (ex: Exception) {
                // Occurs when token request fails, usually an IllegalStateException because refresh token has expired
                clearAuthState()
            }
        }
    }


    override fun invalidateAuthentication() {
        CoroutineScope(Dispatchers.IO).launch {
            clearAuthState()
        }
    }
}