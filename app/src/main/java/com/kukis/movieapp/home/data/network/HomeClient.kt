package com.kukis.movieapp.home.data.network

import com.kukis.movieapp.details.data.network.response.DetailMovieResponse
import com.kukis.movieapp.home.data.network.response.HomeResponse
import retrofit2.Response
import retrofit2.http.GET

interface HomeClient {
    @GET("trending/all/day?language=es-MX")
    suspend fun getTrendingAll(): Response<HomeResponse>

    @GET("trending/movie/day?language=es-MX")
    suspend fun getTrendingMovies(): Response<HomeResponse>
}