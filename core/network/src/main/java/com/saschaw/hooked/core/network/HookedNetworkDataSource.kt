package com.saschaw.hooked.core.network

import com.saschaw.hooked.core.model.FavoritesListPaginated
import com.saschaw.hooked.core.model.RavelryUser

interface HookedNetworkDataSource {
     suspend fun getFavoritesList(): FavoritesListPaginated
     suspend fun getCurrentUser(): RavelryUser
}