package com.kukis.movieapp.details.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.kukis.movieapp.core.components.ScreenDetailLoading
import com.kukis.movieapp.details.domain.model.DetailMovieModel
import com.kukis.movieapp.details.ui.components.ExpandableCard
import com.kukis.movieapp.details.ui.state.DetailMovieState
import com.kukis.movieapp.ui.theme.grayBodyText
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MovieDetailsScreen(id: String, navController: NavHostController) {
    val detailViewModel: DetailViewModel = hiltViewModel()
    val detailMovieState by detailViewModel.detailMovie.collectAsState()
    LaunchedEffect(id) {
        detailViewModel.getDetailMovieVM(id.toInt())
    }
    //ARREGLAR

    when (detailMovieState) {
        is DetailMovieState.Error -> {
            Text(text = "Error: ${(detailMovieState as DetailMovieState.Error).error}")
        }

        DetailMovieState.Loading -> {
            ScreenDetailLoading()
        }

        is DetailMovieState.Success -> {
            val movieDetail = (detailMovieState as DetailMovieState.Success).detailMovie
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item {
                    HeaderImage(detailMovieModel = movieDetail)
                }
                item {
                    Body(detailMovieModel = movieDetail)
                }
                item {
                    Box(modifier = Modifier.height(100.dp))
                }
            }
        }
    }
}

@Composable
fun HeaderImage(detailMovieModel: DetailMovieModel) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    Box(
        modifier = Modifier
            .height(670.dp)
            .width(screenWidth)
            .clipToBounds()
    ) {
        AsyncImage(
            model = "$IMAGE_BASE_URL${detailMovieModel.poster_path}",
            contentDescription = "HeaderMovieDetail${detailMovieModel.id}",
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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                val (hours, minutes) = convertMinutesToHoursAndMinutes(detailMovieModel.runtime)
                Text(
                    text = "$hours HR $minutes MIN",
                    color = grayBodyText,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(end = 16.dp)
                )
                Text(
                    text = detailMovieModel.release_date.split("-")[0],
                    color = grayBodyText,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(end = 16.dp)
                )
            }
        }
    }
}

@Composable
fun Body(detailMovieModel: DetailMovieModel) {
    Column {
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = detailMovieModel.title,
            color = grayBodyText,
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 4.dp)
                .fillMaxWidth()
        )
        Text(
            text = detailMovieModel.overview,
            color = grayBodyText,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 4.dp)
                .fillMaxWidth()
        )
        Row(
            modifier = Modifier
                .padding(horizontal = 11.dp, vertical = 8.dp)
                .padding(top = 15.dp)
                .fillMaxWidth()

        ) {
            detailMovieModel.genres.map {
                Text(
                    text = it.name,
                    color = Color.Black,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .wrapContentSize()
                        .clip(RoundedCornerShape(7.dp))
                        .background(grayBodyText)
                        .padding(1.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
            }
        }
        ExpandableCard(title = "Productoras") {
            Column {
                detailMovieModel.production_companies.map {
                    Text(
                        text = it.name,
                        color = grayBodyText,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}

fun convertMinutesToHoursAndMinutes(totalMinutes: Int): Pair<Int, Int> {
    val hours = totalMinutes / 60
    val minutes = totalMinutes % 60
    return Pair(hours, minutes)
}