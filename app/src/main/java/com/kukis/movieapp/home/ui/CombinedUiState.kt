package com.kukis.movieapp.home.ui

data class CombinedUiState(
    val generalState: HomeUiState = HomeUiState.Loading,
    val moviesState: HomeUiState = HomeUiState.Loading
)
