package com.kukis.movieapp.home.data.network

import com.kukis.movieapp.home.data.network.response.HomeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeService @Inject constructor(private val homeClient: HomeClient) {

    suspend fun getTrendingAll():HomeResponse {
        return withContext(Dispatchers.IO) {
            val response = homeClient.getTrendingAll()
            response.body()!!
        }
    }

    suspend fun getTrendingMovies():HomeResponse {
        return withContext(Dispatchers.IO) {
            val response = homeClient.getTrendingMovies()
            response.body()!!
        }
    }
}