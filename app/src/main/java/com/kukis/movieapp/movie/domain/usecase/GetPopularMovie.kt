package com.kukis.movieapp.movie.domain.usecase

import com.kukis.movieapp.movie.domain.MovieRepository
import javax.inject.Inject

class GetPopularMovie @Inject constructor(private val movieRepository: MovieRepository) {
    suspend operator fun invoke() = movieRepository.getPopular()
}