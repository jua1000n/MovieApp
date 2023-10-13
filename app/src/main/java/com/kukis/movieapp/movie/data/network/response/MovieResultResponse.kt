package com.kukis.movieapp.movie.data.network.response

import com.google.gson.annotations.SerializedName
import com.kukis.movieapp.movie.domain.model.MovieResultModel

data class MovieResultResponse(
    @SerializedName("adult") val adult: Boolean?,
    @SerializedName("backdrop_path") val backdrop_path: String?,
    @SerializedName("genre_ids") val genre_ids: List<Int?>?,
    @SerializedName("id") val id: Int?,
    @SerializedName("original_language") val original_language: String?,
    @SerializedName("original_title") val original_title: String?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("popularity") val popularity: Double?,
    @SerializedName("poster_path") val poster_path: String?,
    @SerializedName("release_date") val release_date: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("video") val video: Boolean?,
    @SerializedName("vote_average") val vote_average: Double?,
    @SerializedName("vote_count") val vote_count: Int?
) {
    fun toDomain(): MovieResultModel {
        return MovieResultModel(
            adult,
            backdropPath = backdrop_path,
            genreIds = genre_ids,
            id,
            originalLanguage = original_language,
            originalTitle = original_title,
            overview,
            popularity,
            posterPath = poster_path,
            releaseDate = release_date,
            title,
            video,
            voteAverage = vote_average,
            voteCount = vote_count
        )
    }
}