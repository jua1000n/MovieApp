package com.kukis.movieapp.details.data.network.response

import com.kukis.movieapp.details.domain.model.DetailSeasonSeriesModel

data class DetailSeasonSeriesResponse(
    val _id: String?,
    val air_date: String?,
    val episodes: List<EpisodeResponse>,
    val id: Int?,
    val name: String?,
    val overview: String?,
    val poster_path: String?,
    val season_number: Int?,
    val vote_average: Double?
) {
    fun toDomain(): DetailSeasonSeriesModel {
        return DetailSeasonSeriesModel(
            _id = _id,
            air_date = air_date,
            episodes = episodes.map { it.toDomain() },
            id = id,
            name = name,
            overview = overview,
            poster_path = poster_path,
            season_number = season_number,
            vote_average = vote_average
        )
    }
}
