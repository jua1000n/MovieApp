package com.kukis.movieapp.details.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kukis.movieapp.details.domain.model.DetailSeasonSeriesModel
import com.kukis.movieapp.details.domain.usecase.GetDetailMovie
import com.kukis.movieapp.details.domain.usecase.GetDetailSeasonSeries
import com.kukis.movieapp.details.domain.usecase.GetDetailSeries
import com.kukis.movieapp.details.ui.state.DetailMovieState
import com.kukis.movieapp.details.ui.state.DetailSeasonSeriesState
import com.kukis.movieapp.details.ui.state.DetailSeriesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getDetailMovie: GetDetailMovie,
    private val getDetailSeries: GetDetailSeries,
    private val getDetailSeasonSeries: GetDetailSeasonSeries
) : ViewModel() {
    private var _detailMovie = MutableStateFlow<DetailMovieState>(DetailMovieState.Loading)
    val detailMovie = _detailMovie

    private var _detailSeries = MutableStateFlow<DetailSeriesState>(DetailSeriesState.Loading)
    val detailSeries = _detailSeries

    private var _detailSeason = MutableStateFlow<DetailSeasonSeriesState>(DetailSeasonSeriesState.Loading)
    val detailSeason = _detailSeason

    fun getDetailMovieVM(id: Int) {
        viewModelScope.launch {
            _detailMovie.value = DetailMovieState.Loading
            val result = withContext(Dispatchers.IO) {
                getDetailMovie(id)
            }
            if (result != null) {
                _detailMovie.value = DetailMovieState.Success(result)
            } else {
                _detailMovie.value =
                    DetailMovieState.Error("Ha ocurrido un error, intentelo mas tarde")
            }
        }
    }

    fun getDetailSeriesVM(id: Int) {
        viewModelScope.launch {
            _detailSeries.value = DetailSeriesState.Loading
            val result = withContext(Dispatchers.IO) {
                getDetailSeries(id)
            }
            if (result != null) {
                _detailSeries.value = DetailSeriesState.Success(result)
            } else {
                _detailSeries.value =
                    DetailSeriesState.Error("Ha ocurrido un error, intentelo mas tarde")
            }
        }
    }

    fun getDetailSeasonSeriesVM(id: Int, season: Int) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                getDetailSeasonSeries(id, season)
            }
            if (result != null) {
                _detailSeason.value = DetailSeasonSeriesState.Success(result)
            } else {
                _detailSeason.value = DetailSeasonSeriesState.Error("Ha ocurrido un error, intentelo mas tarde")
            }
        }
    }
}