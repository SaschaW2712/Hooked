package com.saschaw.hooked.core.model

import kotlinx.serialization.Serializable

data class SearchWithResults(
    val query: String?,
    val results: SearchResultsPaginated
)

@Serializable
data class SearchResultsPaginated(
    val patterns: List<PatternListItem> = emptyList(),
    val paginator: Paginator? = null,
)