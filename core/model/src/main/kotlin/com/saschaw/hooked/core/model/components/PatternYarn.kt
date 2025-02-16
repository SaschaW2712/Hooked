package com.saschaw.hooked.core.model.components

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PatternYarn(
    val permalink: String,
    val id: Int,
    val name: String,
    @SerialName("yarn_company_name") val yarnCompanyName: String,
    @SerialName("yarn_company_id") val yarnCompanyId: Int,
)
