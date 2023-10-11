package com.kukis.movieapp.details.domain.model

data class DetailSeasonSeriesModel(
    val _id: String?,
    val air_date: String?,
    val episodes: List<EpisodeModel>,
    val id: Int?,
    val name: String?,
    val overview: String?,
    val poster_path: String?,
    val season_number: Int?,
    val vote_average: Double?
)