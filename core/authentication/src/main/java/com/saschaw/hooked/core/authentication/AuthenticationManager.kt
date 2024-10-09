package com.saschaw.hooked.core.authentication

import android.content.Intent
import android.net.Uri
import androidx.activity.result.ActivityResult
import androidx.browser.customtabs.CustomTabsIntent
import com.saschaw.hooked.core.datastore.HookedPreferencesDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
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
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

interface AuthenticationManager {
    var authState: AuthState
    fun getAuthorizationIntent(additionalScopes: List<RavelryAuthenticationScope> = emptyList()): Intent
    fun onAuthorizationResult(result: ActivityResult)
    fun doAuthenticated(function: (String?, String?) -> Unit)
    fun refreshTokensIfNeededBlocking()
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
    @Inject
    lateinit var authService: AuthorizationService

    @Inject
    override lateinit var authState: AuthState

    @Inject
    lateinit var preferences: HookedPreferencesDataSource

    private val clientId = AuthConfig.CLIENT_ID
    private val clientSecret = AuthConfig.CLIENT_SECRET
    private val appCallbackUri = AuthConfig.CALLBACK_URL
    private val clientAuthentication: ClientAuthentication = ClientSecretBasic(clientSecret)

    override fun getAuthorizationIntent(additionalScopes: List<RavelryAuthenticationScope>): Intent {
        val authRequest = buildAuthRequest(additionalScopes)

        return authService.getAuthorizationRequestIntent(
            authRequest,
            CustomTabsIntent.Builder().build()
        )
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
                throw exception
            }

            response?.createTokenExchangeRequest()?.let {

                performTokenRequest(
                    authService,
                    clientAuthentication,
                    it
                )
            }
        }
    }

    private fun buildAuthRequest(additionalScopes: List<RavelryAuthenticationScope>?): AuthorizationRequest {
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

        if (!additionalScopes.isNullOrEmpty()) {
            authRequestBuilder.setScope(additionalScopes.joinToString(" "))
        }

        return authRequestBuilder.build()
    }

    private fun performTokenRequest(
        authService: AuthorizationService,
        clientAuthentication: ClientAuthentication,
        tokenRequest: TokenRequest,
    ) {
        authService.performTokenRequest(tokenRequest, clientAuthentication) { response, exception ->
            CoroutineScope(Dispatchers.IO).launch {
                updateAuthState(response, exception)
            }
        }
    }

    private fun performTokenRequestBlocking(
        authService: AuthorizationService,
        clientAuthentication: ClientAuthentication,
        tokenRequest: TokenRequest,
    ) {
        authService.performTokenRequest(tokenRequest, clientAuthentication) { response, exception ->
            runBlocking {
                updateAuthState(response, exception)
            }
        }
    }

    override fun doAuthenticated(function: (String?, String?) -> Unit) {
        authState.performActionWithFreshTokens(authService) { accessToken, idToken, ex ->
            ex?.let {
                // Handle error
                throw ex
            }

            function(accessToken, idToken)
        }
    }

    private suspend fun updateAuthState(authResponse: AuthorizationResponse?, authException: AuthorizationException?) {
        authState.update(authResponse, authException)
        preferences.updateAuthState(authState)
    }

    private suspend fun updateAuthState(tokenResponse: TokenResponse?, authException: AuthorizationException?) {
        authState.update(tokenResponse, authException)
        preferences.updateAuthState(authState)
    }

    override fun refreshTokensIfNeededBlocking() {
        if (authState.needsTokenRefresh) {
            performTokenRequestBlocking(
                authService,
                clientAuthentication,
                authState.createTokenRefreshRequest()
            )
        }
    }
}

//class TokenAuthenticator @Inject constructor(
//    private val authenticationManager: AuthenticationManager
//): Authenticator {
//    override fun authenticate(route: Route?, response: Response): Request? {
//        // Refresh your access_token using a synchronous api request
//        authenticationManager.refreshTokensIfNeededBlocking()
//        val newAccessToken = authenticationManager.authState.accessToken
//
//        // Add new header to rejected request and retry it
//        return response.request.newBuilder()
//            .header("Authorization", "Bearer $newAccessToken")
//            .build()
//    }
//}