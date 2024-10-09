package com.saschaw.hooked.core.network

import com.saschaw.hooked.core.authentication.AuthenticationManager
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.Authenticator
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import javax.inject.Inject
import javax.inject.Singleton

interface RetrofitHookedRavelryApi {
    @GET(value = "people/{username}/favorites/list.json")
    suspend fun getFavoritesList(
        @Path("username") username: String,
        @Header("Authorization") accessToken: String
    ): FavoritesListPaginated
}

@Singleton
internal class RetrofitHookedNetwork @Inject constructor(
    private val authenticationManager: AuthenticationManager,
    private val tokenAuthenticator: Authenticator
): HookedNetworkDataSource {
    private val networkApi =
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(
                Json.asConverterFactory(
                    "application/json; charset=UTF8".toMediaType()
                )
            )
            .build()
            .create(RetrofitHookedRavelryApi::class.java)

    // TODO: Refactor more generic
    override suspend fun refreshFavoritesList(): FavoritesListPaginated {
        val deferred = CompletableDeferred<FavoritesListPaginated>()

        authenticationManager.doAuthenticated { accessToken, _ ->
            accessToken?.let {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val response = networkApi.getFavoritesList("Sajalow", it)
                        deferred.complete(response)
                    } catch (e: Exception) {
                        deferred.completeExceptionally(e)
                    }
                }
            }
        }

        return deferred.await()
    }
}


@Serializable
data class FavoritesListPaginated(
    val favorites: List<FavoritesListItem>,
    val paginator: Paginator
)

@Serializable
data class Paginator(
    @SerialName("page_count") val pageCount: Int,
    val page: Int,
    @SerialName("page_size") val pageSize: Int,
    val results: Int,
    @SerialName("last_page") val lastPage: Int
)

@Serializable
data class FavoritesListItem(
    val comment: String,
    @SerialName("created_at") val createdAt: LocalDateTime
)