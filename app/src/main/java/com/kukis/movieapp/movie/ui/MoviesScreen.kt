package com.kukis.movieapp.movie.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.kukis.movieapp.BuildConfig
import com.kukis.movieapp.movie.ui.state.MovieUiState

@Composable
fun MoviesScreen(navController: NavHostController) {
    val moviesViewModel: MoviesViewModel = hiltViewModel()
    val movie by moviesViewModel.movie.collectAsState()

    when(movie) {
        is MovieUiState.Error -> {}
        MovieUiState.Loading -> {}
        is MovieUiState.Success -> {
            val movieNowPlay = (movie as MovieUiState.Success).movieNowPlay
            val moviePopular = (movie as MovieUiState.Success).moviePopular
            val movieTopRated = (movie as MovieUiState.Success).movieTopRated
            val movieUpcoming = (movie as MovieUiState.Success).movieUpcoming
            LazyColumn {
                item {
                    AsyncImage(
                        model = "${BuildConfig.IMAGE_BASE_URL}${movieNowPlay.results?.get(0)?.posterPath}",
                        contentDescription = ""
                    )
                }
                item {
                    AsyncImage(
                        model = "${BuildConfig.IMAGE_BASE_URL}${moviePopular.results?.get(0)?.posterPath}",
                        contentDescription = ""
                    )
                }
                item {
                    AsyncImage(
                        model = "${BuildConfig.IMAGE_BASE_URL}${movieTopRated.results?.get(0)?.posterPath}",
                        contentDescription = ""
                    )
                }
                item {
                    AsyncImage(
                        model = "${BuildConfig.IMAGE_BASE_URL}${movieUpcoming.results?.get(0)?.posterPath}",
                        contentDescription = ""
                    )
                }
            }
        }
    }
}