package com.kukis.movieapp.movie.ui.state

import com.kukis.movieapp.movie.domain.model.MovieModel

sealed class MovieUiState {
    object Loading : MovieUiState()
    data class Error(val error: String) : MovieUiState()
    data class Success(
        val movieNowPlay: MovieModel,
        val moviePopular: MovieModel,
        val movieTopRated: MovieModel,
        val movieUpcoming: MovieModel
    ) : MovieUiState()
}
