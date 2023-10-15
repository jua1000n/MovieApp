package com.kukis.movieapp.series.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kukis.movieapp.series.ui.state.SeriesUiState
import com.kukis.movieapp.series.domain.usecase.GetAiringSeries
import com.kukis.movieapp.series.domain.usecase.GetOnTheAirSeries
import com.kukis.movieapp.series.domain.usecase.GetPopularSeries
import com.kukis.movieapp.series.domain.usecase.GetTopRatedSeries
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeriesViewModel @Inject constructor(
    private val getAiringSeries: GetAiringSeries,
    private val getPopularSeries: GetPopularSeries,
    private val getTopRatedSeries: GetTopRatedSeries,
    private val getOnTheAirSeries: GetOnTheAirSeries
) : ViewModel() {

    private var _series = MutableStateFlow<SeriesUiState>(SeriesUiState.Loading)
    val series = _series

    init {
        getMovieList()
    }

    private fun getMovieList() {
        viewModelScope.launch {
            _series.value = SeriesUiState.Loading
            val resultAiring = async(Dispatchers.IO) { getAiringSeries() }
            val resultPopular = async(Dispatchers.IO) { getPopularSeries() }
            val resultTopRated = async(Dispatchers.IO) { getTopRatedSeries() }
            val resultOnTheAir = async(Dispatchers.IO) { getOnTheAirSeries() }

            delay(1000)

            val airing = resultAiring.await()
            val popular = resultPopular.await()
            val topRated = resultTopRated.await()
            val onTheAir = resultOnTheAir.await()

            if (airing != null && popular != null && topRated != null && onTheAir != null) {
                _series.value = SeriesUiState.Success(
                    seriesAiring = airing,
                    seriesPopular = popular,
                    seriesTopRated = topRated,
                    seriesOnTheAir = onTheAir
                )
            } else {
                // Al menos uno de los resultados es null
            }
        }
    }
}