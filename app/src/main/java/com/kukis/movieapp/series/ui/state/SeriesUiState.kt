package com.kukis.movieapp.series.ui.state

import com.kukis.movieapp.series.domain.model.SeriesModel

sealed class SeriesUiState {
    object Loading : SeriesUiState()
    data class Error(val error: String) : SeriesUiState()
    data class Success(
        val seriesAiring: SeriesModel,
        val seriesPopular: SeriesModel,
        val seriesTopRated: SeriesModel,
        val seriesOnTheAir: SeriesModel
    ) : SeriesUiState()
}
