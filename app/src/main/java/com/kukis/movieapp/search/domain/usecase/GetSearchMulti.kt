package com.kukis.movieapp.search.domain.usecase

import com.kukis.movieapp.search.domain.SearchRepository
import javax.inject.Inject

class GetSearchMulti @Inject constructor(private val searchRepository: SearchRepository) {
    suspend operator fun invoke(search: String) = searchRepository.getSearchMulti(search)
}