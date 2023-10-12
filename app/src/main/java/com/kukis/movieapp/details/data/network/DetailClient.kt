package com.kukis.movieapp.details.data.network

import com.kukis.movieapp.details.data.network.response.DetailMovieResponse
import com.kukis.movieapp.details.data.network.response.DetailSeasonSeriesResponse
import com.kukis.movieapp.details.data.network.response.DetailSeriesResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailClient {
    @GET("movie/{movie}?language=es-MX")
    suspend fun getDetailMovie(@Path("movie") movie: Int): DetailMovieResponse

    @GET("tv/{tv}?language=es-MX")
    suspend fun getDetailSeries(@Path("tv") series: Int): DetailSeriesResponse

    @GET("tv/{tv}/season/{season}?language=es-MX")
    suspend fun getDetailSeasonSeries(
        @Path("tv") id: Int,
        @Path("season") season: Int
    ): DetailSeasonSeriesResponse
}