@file:OptIn(
    ExperimentalFoundationApi::class
)

package com.kukis.movieapp.home.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.kukis.movieapp.BuildConfig.IMAGE_BASE_URL
import com.kukis.movieapp.home.ui.model.TrendingModel
import com.kukis.movieapp.navigation.ui.model.Routes
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavHostController) {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val combinedState = homeViewModel.uiState.value

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            when (combinedState.generalState) {
                is HomeUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is HomeUiState.Success -> TrendingHome(
                    (combinedState.generalState).lisTrendingModel,
                    navController
                )

                is HomeUiState.Error -> Text(text = "Error: ${(combinedState.generalState).throwable.message}")
            }
        }

        item {
            when (combinedState.moviesState) {
                is HomeUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is HomeUiState.Success -> TrendingMovie(
                    (combinedState.moviesState).lisTrendingModel,
                    navController
                )

                is HomeUiState.Error -> Text(text = "Error en películas: ${(combinedState.moviesState).throwable.message}")
            }
        }

        item {
            Box(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
fun TrendingHome(myTrending: List<TrendingModel>, navController: NavHostController) {

    Column {
        val numberPages = myTrending.size.coerceAtMost(7)
        val pagerState = rememberPagerState(pageCount = { numberPages })

        HorizontalPager(state = pagerState) { page ->
            ItemTrendingHome(trendingModel = myTrending[page], navController)
        }
        AutoScrollPager(pagerState, 5000)
        PagerTrendingHome(pagerState = pagerState, numberPages = numberPages)
    }
}


@Composable
fun AutoScrollPager(pagerState: PagerState, intervalMillis: Long) {
    val coroutineScope = rememberCoroutineScope()
    val flow = flow {
        while (true) {
            delay(intervalMillis)
            emit(Unit)
        }
    }

    LaunchedEffect(pagerState) {
        flow.collect {
            val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
            coroutineScope.launch {
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerTrendingHome(pagerState: PagerState, numberPages: Int) {
    Row(
        Modifier
            .height(50.dp)
            .fillMaxWidth(), horizontalArrangement = Arrangement.Center
    ) {
        repeat(numberPages) { iteration ->
            val color = if (pagerState.currentPage == iteration) Color.White else Color.Gray
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size(9.dp)
            )
        }
    }
}

@Composable
fun ItemTrendingHome(trendingModel: TrendingModel, navController: NavHostController) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    Box(
        modifier = Modifier
            .height(670.dp)
            .width(screenWidth)
            .clipToBounds()
            .clickable {
                when (trendingModel.media_type) {
                    "movie" -> {
                        navController.navigate(Routes.MovieDetails.withId(trendingModel.id.toString()))
                    }

                    "tv" -> {
                        navController.navigate(Routes.SeriesDetails.withId(trendingModel.id.toString()))
                    }
                }
            }
    ) {
        AsyncImage(
            model = "$IMAGE_BASE_URL${trendingModel.poster_path}",
            contentDescription = null,
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
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.BottomCenter)
            ) {
                Text(
                    text = "The stories we love and new ones to discover",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Stream the best movies, series, originals, and more.",
                    color = Color.White,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun TrendingMovie(trendingMovies: List<TrendingModel>, navController: NavHostController) {
    Column {
        Text(text = "Trend movies")
        PagerTrendingMovie(trendingMovies, navController)
    }
}

@Composable
fun PagerTrendingMovie(trendingMovies: List<TrendingModel>, navController: NavHostController) {
    LazyRow(contentPadding = PaddingValues(start = 16.dp)) {
        items(trendingMovies) {
            ItemPagerTrendingMovie(it, navController)
        }
    }
}

@Composable
fun ItemPagerTrendingMovie(trendingModel: TrendingModel, navController: NavHostController) {
    val name = trendingModel.name ?: trendingModel.title
    Box(
        modifier = Modifier
            .width(190.dp)
            .padding(start = 16.dp)
            .height(250.dp)
            .clickable {
                navController.navigate(Routes.MovieDetails.withId(trendingModel.id.toString()))
            }) {
        AsyncImage(
            model = "$IMAGE_BASE_URL${trendingModel.poster_path}",
            contentDescription = "TrendingMovies$name"
        )
    }
}
