package com.kukis.movieapp.series.domain.model

data class SeriesResultModel(
    val backdropPath: String?,
    val firstAirDate: String?,
    val genreIds: List<Int?>?,
    val id: Int?,
    val name: String?,
    val originCountry: List<String?>?,
    val originalLanguage: String?,
    val originalName: String?,
    val overview: String?,
    val popularity: Double?,
    val posterPath: String?,
    val voteAverage: Double?,
    val voteCount: Int?
)