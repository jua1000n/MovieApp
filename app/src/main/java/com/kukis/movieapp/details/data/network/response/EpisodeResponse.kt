package com.kukis.movieapp.details.data.network.response

import com.kukis.movieapp.details.domain.model.EpisodeModel

data class EpisodeResponse(
    val air_date: String?,
    val episode_number: Int?,
    val episode_type: String?,
    val id: Int?,
    val name: String,
    val overview: String?,
    val production_code: String?,
    val runtime: Int,
    val season_number: Int?,
    val show_id: Int?,
    val still_path: String?,
    val vote_average: Double?,
    val vote_count: Int?
) {
    fun toDomain(): EpisodeModel {
        return EpisodeModel(
            air_date,
            episode_number,
            episode_type,
            id,
            name,
            overview,
            production_code,
            runtime,
            season_number,
            show_id,
            still_path,
            vote_average,
            vote_count
        )
    }
}