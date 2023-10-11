package com.kukis.movieapp.details.data.network.response

import com.google.gson.annotations.SerializedName
import com.kukis.movieapp.details.domain.model.DetailSeriesModel
import com.kukis.movieapp.details.domain.model.Genres
import com.kukis.movieapp.details.domain.model.ProductionCompanies
import com.kukis.movieapp.details.domain.model.Seasons

data class DetailSeriesResponse(
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("backdrop_path") val backdrop_path: String,
    @SerializedName("episode_run_time") val episode_run_time: List<Int>,
    @SerializedName("first_air_date") val first_air_date: String,
    @SerializedName("genres") val genres: List<Genres>,
    @SerializedName("id") val id: Int,
    @SerializedName("in_production") val in_production: Boolean,
    @SerializedName("last_air_date") val last_air_date: String,
    @SerializedName("name") val name: String,
    @SerializedName("number_of_episodes") val number_of_episodes: Int,
    @SerializedName("number_of_seasons") val number_of_seasons: Int,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val poster_path: String,
    @SerializedName("production_companies") val production_companies: List<ProductionCompanies>,
    @SerializedName("seasons") val seasons: List<Seasons>,
    @SerializedName("status") val status: String,
    @SerializedName("tagline") val tagline: String,
    @SerializedName("type") val type: String,
    @SerializedName("vote_average") val vote_average: Double
) {
    fun toDomain(): DetailSeriesModel {
        return DetailSeriesModel(
            adult,
            backdrop_path,
            episode_run_time,
            first_air_date,
            genres,
            id,
            in_production,
            last_air_date,
            name,
            number_of_episodes,
            number_of_seasons,
            overview,
            poster_path,
            production_companies,
            seasons,
            status,
            tagline,
            type,
            vote_average
        )
    }
}
