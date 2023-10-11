package com.kukis.movieapp.home.domain

import com.kukis.movieapp.home.data.HomeRepository
import com.kukis.movieapp.home.ui.model.TrendingModel
import javax.inject.Inject

class GetTrendingMoviesCase @Inject constructor(private val repository: HomeRepository) {
    suspend operator fun invoke(): List<TrendingModel> = repository.getTrendingMovies()
}