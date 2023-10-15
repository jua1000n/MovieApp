package com.kukis.movieapp.series.data.network

import com.kukis.movieapp.series.data.network.response.SeriesResponse
import com.kukis.movieapp.series.domain.SeriesRepository
import retrofit2.http.GET

interface SeriesClient {
    @GET("tv/airing_today?language=es-MX&")
    suspend fun getAiringSeries(): SeriesResponse

    @GET("tv/on_the_air?language=es-MX&")
    suspend fun getPopularSeries(): SeriesResponse

    @GET("tv/popular?language=es-MX&")
    suspend fun getTopRatedSeries(): SeriesResponse

    @GET("tv/top_rated?language=es-MX&")
    suspend fun getOnTheAirSeries(): SeriesResponse
}