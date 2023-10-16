package com.kukis.movieapp.search.data.network

import com.kukis.movieapp.search.data.network.response.SearchContentResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchClient {
    @GET("search/multi?include_adult=false&language=es-MX")
    suspend fun getSearchMulti(@Query("query") search: String, @Query("page") page: Int): SearchContentResponse
}