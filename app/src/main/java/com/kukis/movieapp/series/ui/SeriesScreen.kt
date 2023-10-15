package com.kukis.movieapp.series.ui

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
import com.kukis.movieapp.movie.ui.ItemMovie
import com.kukis.movieapp.navigation.ui.model.Routes
import com.kukis.movieapp.series.domain.model.SeriesResultModel
import com.kukis.movieapp.series.ui.state.SeriesUiState
import com.kukis.movieapp.ui.theme.grayBodyText

@Composable
fun SeriesScreen(navController: NavHostController) {
    val seriesViewModel: SeriesViewModel = hiltViewModel()
    val series by seriesViewModel.series.collectAsState()

    when (series) {
        is SeriesUiState.Error -> {
        }

        SeriesUiState.Loading -> {}
        is SeriesUiState.Success -> {
            LazyColumn {
                item {
                    HeaderSeries(series as SeriesUiState.Success, navController)
                }
                item {
                    BodySeries(series as SeriesUiState.Success, navController)
                }
                item {
                    Box(modifier = Modifier.height(100.dp))
                }
            }
        }
    }
}

@Composable
fun BodySeries(success: SeriesUiState.Success, navController: NavHostController) {
    val seriesPopular = success.seriesPopular
    val seriesTopRated = success.seriesTopRated
    val seriesOnTheAir = success.seriesOnTheAir
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        if (seriesPopular.results!!.isNotEmpty()) {
            SeriesRow("Lo mas popular", seriesPopular.results, navController)
        }
        if (seriesTopRated.results!!.isNotEmpty()) {
            SeriesRow("Lo mejor de lo mejor", seriesTopRated.results, navController)
        }
        if (seriesOnTheAir.results!!.isNotEmpty()) {
            SeriesRow("Al aire", seriesOnTheAir.results, navController)
        }
    }
}

@Composable
fun SeriesRow(
    titleRow: String,
    results: List<SeriesResultModel?>,
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
            ItemSeries(it, navController)
        }
    }
}

@Composable
fun ItemSeries(series: SeriesResultModel?, navController: NavHostController) {
    Box(
        modifier = Modifier
            .width(190.dp)
            .padding(start = 16.dp)
            .height(250.dp)
            .clickable {
                navController.navigate(Routes.MovieDetails.withId(series!!.id.toString()))
            }
    ) {
        AsyncImage(
            model = "${BuildConfig.IMAGE_BASE_URL}${series!!.posterPath}",
            contentDescription = "MovieHeaderMoviePopular ${series.id}",
            modifier = Modifier.fillMaxSize()
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HeaderSeries(series: SeriesUiState.Success, navController: NavHostController) {
    val seriesAiring = series.seriesAiring.results
    if (seriesAiring!!.isNotEmpty()) {
        val numberPages = seriesAiring.size.coerceAtMost(7)
        val pagerState = rememberPagerState(pageCount = { numberPages })
        HorizontalPager(state = pagerState) { page ->
            ItemSeriesHeader(seriesAiring[page]!!, navController)
        }
        AutoScrollPager(pagerState = pagerState, intervalMillis = 5000)
        PagerIndicator(pagerState = pagerState, numberPages = numberPages)
    }
}

@Composable
fun ItemSeriesHeader(seriesAiring: SeriesResultModel, navController: NavHostController) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    Box(modifier = Modifier
        .height(670.dp)
        .width(screenWidth)
        .clipToBounds()
        .clickable {
            navController.navigate(Routes.SeriesDetails.withId(seriesAiring.id.toString()))
        }) {
        AsyncImage(
            model = "${BuildConfig.IMAGE_BASE_URL}${seriesAiring.posterPath}",
            contentDescription = "MovieHeaderMovieNowPlay ${seriesAiring.id}",
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
