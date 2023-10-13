package com.kukis.movieapp.movie.data.network.response

import com.google.gson.annotations.SerializedName
import com.kukis.movieapp.movie.domain.model.MovieModel

data class MovieResponse(
    @SerializedName("page") val page: Int?,
    @SerializedName("results") val results: List<MovieResultResponse?>?,
    @SerializedName("total_pages") val total_pages: Int?,
    @SerializedName("total_results") val total_results: Int?
) {
    fun toDomain(): MovieModel {
        return MovieModel(
            page,
            results?.map { it?.toDomain() },
            totalPages =  total_pages,
            totalResults =  total_results
        )
    }
}
