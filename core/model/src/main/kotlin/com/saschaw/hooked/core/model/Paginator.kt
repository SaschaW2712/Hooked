package com.saschaw.hooked.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Paginator(
    @SerialName("page_count") val pageCount: Int,
    val page: Int,
    @SerialName("page_size") val pageSize: Int,
    val results: Int,
    @SerialName("last_page") val lastPage: Int
)
