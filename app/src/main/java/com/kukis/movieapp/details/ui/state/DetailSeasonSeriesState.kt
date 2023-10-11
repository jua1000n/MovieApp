package com.kukis.movieapp.details.ui.state

import com.kukis.movieapp.details.domain.model.DetailSeasonSeriesModel

sealed class DetailSeasonSeriesState {
    object Loading : DetailSeasonSeriesState()
    data class Error(val error: String) : DetailSeasonSeriesState()
    data class Success(val detailSeasonSeriesModel: DetailSeasonSeriesModel) :
        DetailSeasonSeriesState()
}