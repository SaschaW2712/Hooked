package com.saschaw.hooked.core.model.components

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class YarnWeight(
    @SerialName("crochet_gauge") val crochetGauge: String? = null,
    val id: Int,
    @SerialName("knit_gauge") val knitGauge: String? = null,
    @SerialName("max_gauge") val maxGauge: String? = null,
    @SerialName("min_gauge") val minxGauge: String? = null,
    val name: String,
    val ply: String? = null,
    val wpi: String? = null
)