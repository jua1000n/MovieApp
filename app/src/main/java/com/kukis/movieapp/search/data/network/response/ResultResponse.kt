package com.kukis.movieapp.search.data.network.response

import com.google.gson.annotations.SerializedName
import com.kukis.movieapp.search.domain.model.ResultModel

data class ResultResponse(
    @SerializedName("adult") val adult: Boolean?,
    @SerializedName("backdrop_path") val backdrop_path: String?,
    @SerializedName("first_air_date") val first_air_date: String?,
    @SerializedName("genre_ids") val genre_ids: List<Int?>?,
    @SerializedName("id") val id: Int?,
    @SerializedName("media_type") val media_type: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("origin_country") val origin_country: List<String>?,
    @SerializedName("original_language") val original_language: String?,
    @SerializedName("original_name") val original_name: String?,
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
    fun toDomain(): ResultModel {
        return ResultModel(
            adult,
            backdropPath = backdrop_path,
            firstAirDate = first_air_date,
            genreIds = genre_ids,
            id,
            mediaType = media_type,
            name,
            originCountry = origin_country,
            originalLanguage = original_language,
            originalName = original_name,
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