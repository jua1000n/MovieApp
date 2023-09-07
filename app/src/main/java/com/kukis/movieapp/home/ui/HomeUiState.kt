package com.kukis.movieapp.home.ui

import com.kukis.movieapp.home.ui.model.TrendingModel

sealed interface HomeUiState{
    object Loading:HomeUiState
    data class Error(val throwable: Throwable):HomeUiState
    data class Success(val lisTrendingModel:List<TrendingModel>):HomeUiState
}
