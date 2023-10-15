package com.kukis.movieapp.series.domain.usecase

import com.kukis.movieapp.series.domain.SeriesRepository
import javax.inject.Inject

class GetAiringSeries @Inject constructor(private val seriesRepository: SeriesRepository) {
    suspend operator fun invoke() = seriesRepository.getAiringSeries()
}