package com.kukis.movieapp.home.data

import com.kukis.movieapp.home.data.network.HomeService
import com.kukis.movieapp.home.data.network.response.HomeResponse
import com.kukis.movieapp.home.ui.model.TrendingModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepository @Inject constructor(private val api: HomeService) {
    suspend fun getTrendingAll(): List<TrendingModel> = api.getTrendingAll().results

    suspend fun getTrendingMovies(): List<TrendingModel> = api.getTrendingMovies().results
}