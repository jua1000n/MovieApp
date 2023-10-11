package com.kukis.movieapp.details.domain.model

data class DetailMovieModel(
    val adult: Boolean,
    val backdrop_path: String,
    val budget: Int,
    val genres: List<Genres>,
    val id: Int,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val production_companies: List<ProductionCompanies>,
    val release_date: String,
    val revenue: Int,
    val runtime: Int,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean
)
data class Genres(
    val id: Int,
    val name: String
)

data class ProductionCompanies(
    val id: Int,
    val logo_path: String,
    val name: String
)
