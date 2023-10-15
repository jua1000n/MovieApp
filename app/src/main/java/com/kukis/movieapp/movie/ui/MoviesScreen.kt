package com.kukis.movieapp.movie.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.kukis.movieapp.BuildConfig
import com.kukis.movieapp.core.components.AutoScrollPager
import com.kukis.movieapp.core.components.PagerIndicator
import com.kukis.movieapp.movie.domain.model.MovieResultModel
import com.kukis.movieapp.movie.ui.state.MovieUiState
import com.kukis.movieapp.navigation.ui.model.Routes
import com.kukis.movieapp.ui.theme.grayBodyText

@Composable
fun MoviesScreen(navController: NavHostController) {
    val moviesViewModel: MoviesViewModel = hiltViewModel()
    val movie by moviesViewModel.movie.collectAsState()

    when (movie) {
        is MovieUiState.Error -> {}
        MovieUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is MovieUiState.Success -> {
            LazyColumn {
                item {
                    HeaderMovie(movie as MovieUiState.Success, navController)
                }
                item {
                    BodyMovie(movie as MovieUiState.Success, navController)
                }
                item {
                    Box(modifier = Modifier.height(100.dp))
                }
            }
        }
    }
}

@Composable
fun BodyMovie(success: MovieUiState.Success, navController: NavHostController) {
    val moviePopular = success.moviePopular
    val movieTopRated = success.movieTopRated
    val movieUpcoming = success.movieUpcoming
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        if (moviePopular.results!!.isNotEmpty()) {
            MovieRow("Lo mas popular", moviePopular.results, navController)
        }
        if (movieTopRated.results!!.isNotEmpty()) {
            MovieRow("Las mejores por siempre", movieTopRated.results, navController)
        }
        if (movieUpcoming.results!!.isNotEmpty()) {
            MovieRow("Proximamente", movieUpcoming.results, navController)
        }
    }
}

@Composable
fun MovieRow(
    titleRow: String,
    results: List<MovieResultModel?>,
    navController: NavHostController
) {
    Text(
        text = titleRow,
        modifier = Modifier.padding(16.dp),
        color = grayBodyText,
        fontWeight = FontWeight.Bold,
        fontSize = 23.sp
    )
    LazyRow(
        contentPadding = PaddingValues(start = 16.dp),
        modifier = Modifier.padding(bottom = 32.dp)
    ) {
        items(results) {
            ItemMovie(it, navController)
        }
    }
}

@Composable
fun ItemMovie(movie: MovieResultModel?, navController: NavHostController) {
    Box(
        modifier = Modifier
            .width(190.dp)
            .padding(start = 16.dp)
            .height(250.dp)
            .clickable {
                navController.navigate(Routes.MovieDetails.withId(movie!!.id.toString()))
            }
    ) {
        AsyncImage(
            model = "${BuildConfig.IMAGE_BASE_URL}${movie!!.posterPath}",
            contentDescription = "MovieHeaderMoviePopular ${movie.id}",
            modifier = Modifier.fillMaxSize()
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HeaderMovie(movie: MovieUiState.Success, navController: NavHostController) {
    val movieNowPlay = movie.movieNowPlay.results
    if (movieNowPlay!!.isNotEmpty()) {
        val numberPages = movieNowPlay.size.coerceAtMost(7)
        val pagerState = rememberPagerState(pageCount = { numberPages })
        HorizontalPager(state = pagerState) { page ->
            ItemMovieHeader(movieNowPlay[page]!!, navController)
        }
        AutoScrollPager(pagerState = pagerState, intervalMillis = 5000)
        PagerIndicator(pagerState = pagerState, numberPages = numberPages)
    }
}

@Composable
fun ItemMovieHeader(movieNowPlay: MovieResultModel, navController: NavHostController) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    Box(
        modifier = Modifier
            .height(670.dp)
            .width(screenWidth)
            .clipToBounds()
            .clickable {
                navController.navigate(Routes.MovieDetails.withId(movieNowPlay.id.toString()))
            }
    ) {
        AsyncImage(
            model = "${BuildConfig.IMAGE_BASE_URL}${movieNowPlay.posterPath}",
            contentDescription = "MovieHeaderMovieNowPlay ${movieNowPlay.id}",
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.BottomCenter),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .height(300.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent, Color.Black
                        ), endY = 650f
                    )
                )
        )
    }
}