package com.saschaw.hooked.core.model.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RavelryPatternUser(
    val id: Int,
    val username: String,
    @SerialName("large_photo_url") val largePhotoUrl: String? = null,
    @SerialName("photo_url") val photoUrl: String? = null,
    @SerialName("small_photo_url") val smallPhotoUrl: String? = null,
    @SerialName("tiny_photo_url") val tinyPhotoUrl: String? = null,
)