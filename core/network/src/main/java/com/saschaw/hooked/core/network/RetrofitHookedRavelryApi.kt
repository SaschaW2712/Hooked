package com.saschaw.hooked.core.network

import com.saschaw.hooked.core.authentication.AuthenticationManager
import com.saschaw.hooked.core.datastore.PreferencesDataSource
import com.saschaw.hooked.core.model.FavoritesListPaginated
import com.saschaw.hooked.core.model.RavelryUser
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
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
    ): FavoritesListPaginated

    @GET(value = "/current_user.json")
    suspend fun getCurrentUser(
        @Header("Authorization") accessToken: String,
    ): CurrentUserResponse
}

@Serializable
data class CurrentUserResponse(
    val ravelryUser: RavelryUser
)

@Singleton
internal class RetrofitHookedNetwork @Inject constructor(
    private val preferencesDataSource: PreferencesDataSource,
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

    // TODO: Refactor more generically
    override suspend fun getFavoritesList(): FavoritesListPaginated {
        val deferred = CompletableDeferred<FavoritesListPaginated>()

        authenticationManager.doAuthenticated(
            function = { accessToken, _ ->
                accessToken?.let {
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            // For now, we only want pattern favorites
                            val types = arrayOf("pattern")

                            val response = networkApi.getFavoritesList("Sajalow", "Bearer $it", types)
                            deferred.complete(response)
                        } catch (e: Exception) {
                            deferred.completeExceptionally(e)
                        }
                    }
                }
            },
            onError = {
                deferred.completeExceptionally(it)
            }
        )

        return deferred.await()
    }

    override suspend fun getCurrentUser(): RavelryUser {
        val deferred = CompletableDeferred<RavelryUser>()

        authenticationManager.doAuthenticated(
            function = { accessToken, _ ->
                accessToken?.let {
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val response = networkApi.getCurrentUser("Bearer $it")
                            preferencesDataSource.updateAppUserData(ravelryUser = response.ravelryUser)
                            deferred.complete(response.ravelryUser)
                        } catch (e: Exception) {
                            deferred.completeExceptionally(e)
                        }
                    }
                }
            },
            onError = {
                deferred.completeExceptionally(it)
            }
        )

        try {
            return deferred.await()
        } catch (ex: Exception) {
            authenticationManager.invalidateAuthentication()
            throw ex
        }

    }
}

