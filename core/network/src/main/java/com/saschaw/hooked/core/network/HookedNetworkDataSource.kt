package com.saschaw.hooked.core.network

import com.saschaw.hooked.core.model.FavoritesListPaginated
import com.saschaw.hooked.core.model.User

interface HookedNetworkDataSource {
     suspend fun getFavoritesList(): FavoritesListPaginated
     suspend fun getCurrentUser(): User
}