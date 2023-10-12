package com.kukis.movieapp.home.ui.model

import com.google.gson.annotations.SerializedName

data class TrendingModel(
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("backdrop_path") val backdrop_path: String,
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("original_language") val original_language: String,
    @SerializedName("original_title") val original_title: String = "",
    @SerializedName("original_name") val original_name: String = "",
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val poster_path: String,
    @SerializedName("media_type") val media_type: String,
    @SerializedName("genre_ids") val genre_ids: List<Int>,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("release_date") val release_date: String = "",
    @SerializedName("first_air_date") val first_air_date: String = "",
    @SerializedName("video") val video: Boolean = false,
    @SerializedName("vote_average") val vote_average: Double,
    @SerializedName("vote_count") val vote_count: Int,
    @SerializedName("origin_country") val origin_country: List<String>
)
