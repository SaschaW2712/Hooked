package com.saschaw.hooked.core.model

import kotlinx.serialization.Serializable

@Serializable
data class SearchResultsPaginated(
    val patterns: List<PatternListItem> = emptyList(),
    val paginator: Paginator? = null,
)