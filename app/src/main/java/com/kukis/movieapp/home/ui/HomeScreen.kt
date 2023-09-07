@file:OptIn(
    ExperimentalFoundationApi::class
)

package com.kukis.movieapp.home.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import coil.compose.AsyncImage
import com.kukis.movieapp.home.ui.model.TrendingModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/original"

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(homeViewModel: HomeViewModel) {
    when (val uiState = homeViewModel.uiState.value) {
        is HomeUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is HomeUiState.Success -> {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item { TrendingHome(uiState.lisTrendingModel) }
            }
        }

        is HomeUiState.Error -> {
            Text(text = "Error: ${uiState.throwable.message}")
        }
    }
}

@Composable
fun TrendingHome(myTrending: List<TrendingModel>) {

    Column {
        val numberPages = myTrending.size.coerceAtMost(7)
        val pagerState = rememberPagerState(pageCount = { numberPages })

        HorizontalPager(state = pagerState) { page ->
            ItemTrendingHome(trendingModel = myTrending[page])
        }
        AutoScrollPager(pagerState, 5000)
        PagerIndicators(pagerState = pagerState, numberPages = numberPages)
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


@Composable
fun PagerIndicators(pagerState: PagerState, numberPages: Int) {
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
fun ItemTrendingHome(trendingModel: TrendingModel) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    Box(
        modifier = Modifier
            .height(670.dp)
            .width(screenWidth)
            .clipToBounds()
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



