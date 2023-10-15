package com.kukis.movieapp.series.domain

import com.kukis.movieapp.series.domain.model.SeriesModel

interface SeriesRepository {
    suspend fun getAiringSeries(): SeriesModel?
    suspend fun getPopularSeries(): SeriesModel?
    suspend fun getTopRatedSeries(): SeriesModel?
    suspend fun getOnTheAirSeries(): SeriesModel?

}