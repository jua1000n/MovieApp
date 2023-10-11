package com.kukis.movieapp.details.domain.model

data class DetailSeriesModel(
    val adult: Boolean,
    val backdrop_path: String,
    val episode_run_time: List<Int>,
    val first_air_date: String,
    val genres: List<Genres>,
    val id: Int,
    val in_production: Boolean,
    val last_air_date: String,
    val name: String,
    val number_of_episodes: Int,
    val number_of_seasons: Int,
    val overview: String,
    val poster_path: String,
    val production_companies: List<ProductionCompanies>,
    val seasons: List<Seasons>,
    val status: String,
    val tagline: String,
    val type: String,
    val vote_average: Double
)

data class Seasons(
    val air_date: String,
    val episode_count: Int,
    val id: Int,
    val name: String,
    val overview: String,
    val poster_path: String,
    val season_number: Int,
    val vote_average: Double
)



