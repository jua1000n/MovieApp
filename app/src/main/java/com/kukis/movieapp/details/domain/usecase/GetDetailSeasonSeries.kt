package com.kukis.movieapp.details.domain.usecase

import com.kukis.movieapp.details.domain.DetailRepository
import javax.inject.Inject

class GetDetailSeasonSeries @Inject constructor(private val detailRepository: DetailRepository) {
    suspend operator fun invoke(id: Int, season: Int) =
        detailRepository.getDetailSeasonSeries(id, season)
}