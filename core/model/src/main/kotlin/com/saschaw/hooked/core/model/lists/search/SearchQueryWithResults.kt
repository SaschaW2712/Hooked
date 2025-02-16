package com.saschaw.hooked.core.model.lists.search

data class SearchQueryWithResults(
    val query: String?,
    val results: SearchResultsPaginated
)

