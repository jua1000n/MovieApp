package com.kukis.movieapp.home.domain

import com.kukis.movieapp.home.data.HomeRepository
import com.kukis.movieapp.home.ui.model.TrendingModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTrendingAllCase @Inject constructor(private val repository: HomeRepository) {
    suspend operator fun invoke(): List<TrendingModel> = repository.getTrendingAll()
}
