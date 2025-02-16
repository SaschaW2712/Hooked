package com.saschaw.hooked.core.model.components

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class YarnWeight(
    @SerialName("crochet_gauge") val crochetGauge: String?,
    val id: Int,
    @SerialName("knit_gauge") val knitGauge: String?,
    @SerialName("max_gauge") val maxGauge: String?,
    @SerialName("min_gauge") val minxGauge: String?,
    val name: String,
    val ply: String?,
    val wpi: String?
)