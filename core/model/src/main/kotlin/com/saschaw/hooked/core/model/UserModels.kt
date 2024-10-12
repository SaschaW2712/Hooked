package com.saschaw.hooked.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RavelryUser(
    val id: Int,
    val username: String,
)

@Serializable
data class PatternAuthor(
    val id: Int,
    val name: String,
    val permalink: String,
    val users: List<RavelryPatternUser>,
)

@Serializable
data class RavelryPatternUser(
    val id: Int,
    val username: String,
    @SerialName("large_photo_url") val largePhotoUrl: String? = null,
    @SerialName("photo_url") val photoUrl: String? = null,
    @SerialName("small_photo_url") val smallPhotoUrl: String? = null,
    @SerialName("tiny_photo_url") val tinyPhotoUrl: String? = null,
)