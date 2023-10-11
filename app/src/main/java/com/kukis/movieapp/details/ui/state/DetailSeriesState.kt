package com.kukis.movieapp.details.ui.state

import com.kukis.movieapp.details.domain.model.DetailSeriesModel

sealed class DetailSeriesState {
    object Loading : DetailSeriesState()
    data class Error(val error: String) : DetailSeriesState()
    data class Success(val detailSeries: DetailSeriesModel) : DetailSeriesState()
}