package com.kukis.movieapp.search.domain.model

data class SearchContentModel(
    val page: Int,
    val results: List<ResultModel?>?,
    val totalPages: Int,
    val totalResults: Int
)