package com.kukis.movieapp.movie.domain.model

import com.google.gson.annotations.SerializedName

data class MovieModel(
    val page: Int?,
    val results: List<MovieResultModel?>?,
    val totalPages: Int?,
    val totalResults: Int?
)