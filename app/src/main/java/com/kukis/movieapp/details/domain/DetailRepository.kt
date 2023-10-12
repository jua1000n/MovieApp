package com.kukis.movieapp.details.domain

import com.kukis.movieapp.details.domain.model.DetailMovieModel
import com.kukis.movieapp.details.domain.model.DetailSeasonSeriesModel
import com.kukis.movieapp.details.domain.model.DetailSeriesModel

interface DetailRepository {
    suspend fun getDetailMovie(id: Int): DetailMovieModel?

    suspend fun getDetailSeries(id: Int): DetailSeriesModel?

    suspend fun getDetailSeasonSeries(id: Int, season: Int): DetailSeasonSeriesModel?
}