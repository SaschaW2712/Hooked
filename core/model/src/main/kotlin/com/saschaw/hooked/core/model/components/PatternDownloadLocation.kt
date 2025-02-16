package com.saschaw.hooked.core.model.components

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PatternDownloadLocation(
    val url: String,
    val free: Boolean,
    @SerialName("type") private val typeString: String
) {
    val type: DownloadLocationType
        get() = when (typeString) {
            "ravelry" -> DownloadLocationType.Ravelry
            "external" -> DownloadLocationType.External
            else -> throw IllegalArgumentException("Unknown download location type: $typeString")
        }
}

@Serializable
enum class DownloadLocationType {
    Ravelry,
    External
}