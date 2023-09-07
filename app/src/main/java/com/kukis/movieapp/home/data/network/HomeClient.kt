package com.kukis.movieapp.home.data.network

import com.kukis.movieapp.home.data.network.response.HomeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface HomeClient {
    @GET("trending/all/day?language=es-MX")
    @Headers(
        "accept: application/json",
        "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxMDgxYjFmZWFiNGU1MzAzOGFlMzZmYmM4OThhMTNhYSIsInN1YiI6IjY0NDg0NzU3NmEyMjI3MDRmOGQxMGJhOCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.vDtGlZYXuJiTcLnii03meq1nnRJ74TZFE5xCNcctc3k"
    )
    suspend fun getTrendingAll():Response<HomeResponse>
}