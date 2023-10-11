package com.kukis.movieapp.details.domain.usecase

import com.kukis.movieapp.details.domain.DetailRepository
import javax.inject.Inject

class GetDetailSeries @Inject constructor(private val detailRepository: DetailRepository) {
    suspend operator fun invoke(id:Int) = detailRepository.getDetailSeries(id)
}