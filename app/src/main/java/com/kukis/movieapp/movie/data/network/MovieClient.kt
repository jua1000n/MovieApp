package com.kukis.movieapp.movie.data.network

import com.kukis.movieapp.movie.data.network.response.MovieResponse
import retrofit2.http.GET

interface MovieClient {
    @GET("movie/now_playing?language=es-MX")
    suspend fun getNowPlaying(): MovieResponse
    @GET("movie/popular?language=es-MX")
    suspend fun getPopular(): MovieResponse
    @GET("movie/top_rated?language=es-MX")
    suspend fun getTopRated(): MovieResponse
    @GET("movie/upcoming?language=es-MX")
    suspend fun getUpcoming(): MovieResponse
}