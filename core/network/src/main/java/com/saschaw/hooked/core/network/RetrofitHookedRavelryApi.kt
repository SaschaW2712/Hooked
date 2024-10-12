package com.saschaw.hooked.core.network

import android.util.Log
import com.saschaw.hooked.core.authentication.AuthenticationManager
import com.saschaw.hooked.core.model.FavoritesListPaginated
import com.saschaw.hooked.core.model.RavelryUser
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

interface RetrofitHookedRavelryApi {
    @GET(value = "/people/{username}/favorites/list.json")
    suspend fun getFavoritesList(
        @Path("username") username: String,
        @Header("Authorization") accessToken: String,
        @Query("types") types: Array<String>
    ): FavoritesListPaginated?

    @GET(value = "/current_user.json")
    suspend fun getCurrentUser(
        @Header("Authorization") accessToken: String,
    ): CurrentUserResponse
}

@Serializable
data class CurrentUserResponse(
    @SerialName("user") val ravelryUser: RavelryUser
)

@Singleton
internal class RetrofitHookedNetwork @Inject constructor(
    private val authenticationManager: AuthenticationManager,
): HookedNetworkDataSource {
    private val networkJson = Json { ignoreUnknownKeys = true }

    private val networkApi =
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(
                networkJson.asConverterFactory(
                    "application/json; charset=utf-8".toMediaType()
                )
            )
            .build()
            .create(RetrofitHookedRavelryApi::class.java)

    private var lastFetchedUsername: String? = null

    override suspend fun fetchFavoritesList(): FavoritesListPaginated? {
        val deferred = CompletableDeferred<FavoritesListPaginated?>()

        authenticationManager.doAuthenticated(
            function = { accessToken, _ ->
                accessToken?.let { token ->
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            // For now, we only want pattern favorites
                            val types = arrayOf("pattern")

                            val username = lastFetchedUsername ?: fetchCurrentUsername()

                            username?.let {
                                val response = networkApi.getFavoritesList(
                                    it,
                                    getAuthHeaderValue(token),
                                    types
                                )
                                deferred.complete(response)
                            }

                        } catch (e: Exception) {
                            deferred.completeExceptionally(e)
                        }
                    }
                }
            },
            onFailure = {
                deferred.completeExceptionally(it)
            }
        )

        try {
            return deferred.await()
        } catch (e: Exception) {
            Log.e("Network", "Error getting favorites list", e)
            if (e is HttpException && e.code() == 403) {
                authenticationManager.invalidateAuthentication()
            }
            return null
        }
    }

    override suspend fun fetchCurrentUsername(): String? {
        val deferred = CompletableDeferred<String>()

        authenticationManager.doAuthenticated(
            function = { accessToken, _ ->
                accessToken?.let {
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val response = networkApi.getCurrentUser(getAuthHeaderValue(it))
                            lastFetchedUsername = response.ravelryUser.username
                            deferred.complete(response.ravelryUser.username)
                        } catch (e: Exception) {
                            deferred.completeExceptionally(e)
                        }
                    }
                }
            },
            onFailure = {
                deferred.completeExceptionally(it)
            }
        )

        return try {
            deferred.await()
        } catch (e: Exception) {
            Log.e("Network", "Error getting username", e)
            if (e is HttpException && e.code() == 403) {
                authenticationManager.invalidateAuthentication()
            }
            null
        }
    }

    private fun getAuthHeaderValue(accessToken: String): String = "Bearer $accessToken"
}

