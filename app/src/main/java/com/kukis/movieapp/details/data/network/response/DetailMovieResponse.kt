package com.kukis.movieapp.details.data.network.response

import com.google.gson.annotations.SerializedName
import com.kukis.movieapp.details.domain.model.DetailMovieModel
import com.kukis.movieapp.details.domain.model.Genres
import com.kukis.movieapp.details.domain.model.ProductionCompanies

data class DetailMovieResponse(
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("backdrop_path") val backdrop_path: String,
    @SerializedName("budget") val budget: Int,
    @SerializedName("genres") val genres: List<Genres>,
    @SerializedName("id") val id: Int,
    @SerializedName("overview") val overview: String,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("poster_path") val poster_path: String,
    @SerializedName("production_companies") val production_companies:List<ProductionCompanies>,
    @SerializedName("release_date") val release_date: String,
    @SerializedName("revenue") val revenue: Int,
    @SerializedName("runtime") val runtime: Int,
    @SerializedName("status") val status: String,
    @SerializedName("tagline") val tagline: String,
    @SerializedName("title") val title: String,
    @SerializedName("video") val video: Boolean
) {
    fun toDomain(): DetailMovieModel {
        return DetailMovieModel(
            adult,
            backdrop_path,
            budget,
            genres,
            id,
            overview,
            popularity,
            poster_path,
            production_companies,
            release_date,
            revenue,
            runtime,
            status,
            tagline,
            title,
            video
        )
    }
}