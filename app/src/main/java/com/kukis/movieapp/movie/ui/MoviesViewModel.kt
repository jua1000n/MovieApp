package com.kukis.movieapp.movie.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kukis.movieapp.movie.domain.usecase.GetNowPlayingMovie
import com.kukis.movieapp.movie.domain.usecase.GetPopularMovie
import com.kukis.movieapp.movie.domain.usecase.GetTopRatedMovie
import com.kukis.movieapp.movie.domain.usecase.GetUpcomingMovie
import com.kukis.movieapp.movie.ui.state.MovieUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getNowPlayingMovie: GetNowPlayingMovie,
    private val getPopular: GetPopularMovie,
    private val getTopRated: GetTopRatedMovie,
    private val getUpcoming: GetUpcomingMovie
) : ViewModel() {

    private var _movie = MutableStateFlow<MovieUiState>(MovieUiState.Loading)
    val movie = _movie

    init {
        getMovieList()
    }

    private fun getMovieList() {
        viewModelScope.launch {
            _movie.value = MovieUiState.Loading
            val resultNowPlaying = async(Dispatchers.IO) { getNowPlayingMovie() }
            val resultPopular = async(Dispatchers.IO) { getPopular() }
            val resultTopRated = async(Dispatchers.IO) { getTopRated() }
            val resultUpcoming = async(Dispatchers.IO) { getUpcoming() }

            delay(1000)

            val nowPlaying = resultNowPlaying.await()
            val popular = resultPopular.await()
            val topRated = resultTopRated.await()
            val upcoming = resultUpcoming.await()

            if (nowPlaying != null && popular != null && topRated != null && upcoming != null) {
                _movie.value = MovieUiState.Success(
                    movieNowPlay = nowPlaying,
                    moviePopular = popular,
                    movieTopRated = topRated,
                    movieUpcoming = upcoming
                )
            } else {
                // Al menos uno de los resultados es null
            }
        }
    }
}