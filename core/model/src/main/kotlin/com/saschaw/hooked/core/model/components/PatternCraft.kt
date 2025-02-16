package com.saschaw.hooked.core.model.components

import kotlinx.serialization.Serializable

@Serializable
data class PatternCraft(
    val id: Int,
    val name: String,
    val permalink: String,
) {
    enum class CraftType {
        Knitting,
        Crochet,
        Other
    }

    val type: CraftType
        get() = when (name) {
            "Knitting" -> CraftType.Knitting
            "Crochet" -> CraftType.Crochet
            else -> CraftType.Other
        }
}