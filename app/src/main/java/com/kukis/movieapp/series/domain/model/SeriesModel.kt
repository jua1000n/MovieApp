package com.kukis.movieapp.series.domain.model

data class SeriesModel(
    val page: Int?,
    val results: List<SeriesResultModel?>?,
    val totalPages: Int?,
    val totalResults: Int?
)