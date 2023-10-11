package com.kukis.movieapp.details.ui.state

import com.kukis.movieapp.details.domain.model.DetailMovieModel

sealed class DetailMovieState {
    object Loading : DetailMovieState()
    data class Error(val error: String) : DetailMovieState()
    data class Success(val detailMovie: DetailMovieModel) : DetailMovieState()
}