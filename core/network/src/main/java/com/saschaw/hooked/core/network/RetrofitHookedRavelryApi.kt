package com.saschaw.hooked.core.network

import com.saschaw.hooked.core.authentication.AuthenticationManager
import com.saschaw.hooked.core.model.FavoritesListPaginated
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
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
}

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

    // TODO: Refactor more generic
    override suspend fun refreshFavoritesList(): FavoritesListPaginated {
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
}

