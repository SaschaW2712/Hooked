package com.saschaw.hooked.core.model.lists.search

import com.saschaw.hooked.core.model.lists.Paginator
import com.saschaw.hooked.core.model.pattern.PatternListItem
import kotlinx.serialization.Serializable

@Serializable
data class SearchResultsPaginated(
    val patterns: List<PatternListItem> = emptyList(),
    val paginator: Paginator? = null,
)