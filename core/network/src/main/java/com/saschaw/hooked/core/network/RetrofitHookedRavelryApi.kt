package com.saschaw.hooked.core.network

import android.util.Log
import com.saschaw.hooked.core.authentication.AuthenticationManager
import com.saschaw.hooked.core.model.Bookmark
import com.saschaw.hooked.core.model.CreateBookmark
import com.saschaw.hooked.core.model.lists.favorites.FavoritesListPaginated
import com.saschaw.hooked.core.model.user.RavelryUser
import com.saschaw.hooked.core.model.lists.search.SearchResultsPaginated
import com.saschaw.hooked.core.model.pattern.PatternFull
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
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

interface RetrofitHookedRavelryApi {
    @GET(value = "/people/{username}/favorites/list.json")
    suspend fun getFavoritesList(
        @Path("username") username: String,
        @Header("Authorization") accessToken: String,
        @Query("types") types: Array<String>,
        @Query("page_size") pageSize: Int
    ): FavoritesListPaginated?

    @GET(value = "/patterns/search.json")
    suspend fun search(
        @Header("Authorization") accessToken: String,
        @Query("query") searchQuery: String,
    ): SearchResultsPaginated

    @GET(value = "/patterns/{id}.json")
    suspend fun getPatternDetails(
        @Path("id") id: Int,
        @Header("Authorization") accessToken: String,
    ): PatternDetailsResponse

    @GET(value = "/current_user.json")
    suspend fun getCurrentUser(
        @Header("Authorization") accessToken: String,
    ): CurrentUserResponse

    @POST(value = "/people/{username}/favorites/create.json")
    suspend fun saveToFavorites(
        @Path("username") username: String,
        @Header("Authorization") accessToken: String,
        @Body bookmark: CreateBookmark
    ): SaveToFavoritesResponse

    @DELETE(value = "/people/{username}/favorites/{id}.json")
    suspend fun deleteFromFavorites(
        @Path("username") username: String,
        @Path("id") id: Int,
        @Header("Authorization") accessToken: String,
    ): SaveToFavoritesResponse
}

@Serializable
data class CurrentUserResponse(
    @SerialName("user") val ravelryUser: RavelryUser
)

@Serializable
data class PatternDetailsResponse(
    val pattern: PatternFull?,
)

@Serializable
data class SaveToFavoritesResponse(
    val bookmark: Bookmark,
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
                                    username = it,
                                    accessToken = getAuthHeaderValue(token),
                                    pageSize = 100,
                                    types = types
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

        return resultOrInvalidAuth(deferred)
    }

    override suspend fun search(query: String): SearchResultsPaginated? {
        val deferred = CompletableDeferred<SearchResultsPaginated?>()

        authenticationManager.doAuthenticated(
            function = { accessToken, _ ->
                accessToken?.let { token ->
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val response = networkApi.search(getAuthHeaderValue(token), query)
                            deferred.complete(response)
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

        return resultOrInvalidAuth(deferred)
    }

    override suspend fun fetchPatternDetails(id: Int): PatternDetailsResponse? {
        val deferred = CompletableDeferred<PatternDetailsResponse?>()

        authenticationManager.doAuthenticated(
            function = { accessToken, _ ->
                accessToken?.let { token ->
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val response = networkApi.getPatternDetails(id, getAuthHeaderValue(token))
                            deferred.complete(response)
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

        return resultOrInvalidAuth(deferred)
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

        return resultOrInvalidAuth(deferred)
    }

    override suspend fun savePatternToFavorites(id: Int): Bookmark? {
        val deferred = CompletableDeferred<Bookmark>()

        authenticationManager.doAuthenticated(
            function = { accessToken, _ ->
                accessToken?.let { token ->
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val bookmark = CreateBookmark(id.toString(), "pattern", "", null)

                            val username = lastFetchedUsername ?: fetchCurrentUsername()

                            username?.let {
                                val response = networkApi.saveToFavorites(
                                    username = it,
                                    accessToken = getAuthHeaderValue(token),
                                    bookmark = bookmark
                                )

                                deferred.complete(response.bookmark)
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

        return resultOrInvalidAuth(deferred)
    }

    override suspend fun removePatternFromFavorites(id: Int): Bookmark? {
        val deferred = CompletableDeferred<Bookmark>()

        authenticationManager.doAuthenticated(
            function = { accessToken, _ ->
                accessToken?.let { token ->
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            lastFetchedUsername ?: fetchCurrentUsername()?.let {
                                val response = networkApi.deleteFromFavorites(
                                    username = it,
                                    id = id,
                                    accessToken = getAuthHeaderValue(token),
                                )

                                deferred.complete(response.bookmark)
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

        return resultOrInvalidAuth(deferred)
    }

    private suspend fun <T> resultOrInvalidAuth(deferred: CompletableDeferred<T>): T? {
        return try {
            deferred.await()
        } catch (e: Exception) {
            Log.e("Network", "Error getting result", e)
            if (e is HttpException && (e.code() == 403 || e.code() == 401)) {
                authenticationManager.invalidateAuthentication()
            }
            null
        }
    }

    private fun getAuthHeaderValue(accessToken: String): String = "Bearer $accessToken"
}