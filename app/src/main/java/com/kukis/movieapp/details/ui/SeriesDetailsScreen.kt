@file:OptIn(ExperimentalMaterial3Api::class)

package com.kukis.movieapp.details.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
import com.kukis.movieapp.BuildConfig
import com.kukis.movieapp.details.domain.model.DetailSeriesModel
import com.kukis.movieapp.details.domain.model.Seasons
import com.kukis.movieapp.details.ui.components.ExpandableCard
import com.kukis.movieapp.details.ui.state.DetailSeasonSeriesState
import com.kukis.movieapp.details.ui.state.DetailSeriesState
import com.kukis.movieapp.ui.theme.background_second
import com.kukis.movieapp.ui.theme.grayBodyText
import com.kukis.movieapp.ui.theme.seasonRuntime
import com.kukis.movieapp.ui.theme.whiteTitle
import kotlin.math.pow

@Composable
fun SeriesDetailsScreen(id: String, navController: NavHostController) {
    val detailViewModel: DetailViewModel = hiltViewModel()
    val detailSeriesState by detailViewModel.detailSeries.collectAsState()
    LaunchedEffect(id) {
        detailViewModel.getDetailSeriesVM(id.toInt())
    }
    when (detailSeriesState) {
        is DetailSeriesState.Error -> {
            Text(text = "Error: ${(detailViewModel as DetailSeriesState.Error).error}")
        }

        DetailSeriesState.Loading -> {
            StateLoading()
        }

        is DetailSeriesState.Success -> {
            StateSuccess(detailSeriesState)
        }
    }
}

@Composable
fun StateLoading() {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun StateSuccess(detailSeriesState: DetailSeriesState) {
    val seriesDetail = (detailSeriesState as DetailSeriesState.Success).detailSeries
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            HeaderImage(detailSeriesModel = seriesDetail)
        }
        item {
            Body(detailSeriesModel = seriesDetail)
        }
        item {
            Box(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
fun HeaderImage(detailSeriesModel: DetailSeriesModel) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    Box(
        modifier = Modifier
            .height(670.dp)
            .width(screenWidth)
            .clipToBounds()
    ) {
        AsyncImage(
            model = "${BuildConfig.IMAGE_BASE_URL}${detailSeriesModel.poster_path}",
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
            HeaderContentImage(Modifier.align(Alignment.BottomStart), detailSeriesModel)
        }
    }
}

@Composable
fun HeaderContentImage(modifier: Modifier, detailSeriesModel: DetailSeriesModel) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = round(detailSeriesModel.vote_average, 2).toString(),
            color = grayBodyText,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
        Icon(
            imageVector = Icons.Rounded.Star,
            contentDescription = "vote_average",
            modifier = Modifier
                .padding(end = 16.dp)
                .size(16.dp),
            tint = grayBodyText
        )
        Text(
            text = detailSeriesModel.tagline,
            color = grayBodyText,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(end = 16.dp)
        )
    }
}

@Composable
fun Body(detailSeriesModel: DetailSeriesModel) {
    Column {
        SeriesBasicInfo(detailSeriesModel) // Title, overview, genres
        SheetScreen(detailSeriesModel)
        ExpandableCard(title = "Productoras") {
            Column {
                detailSeriesModel.production_companies.map {
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

@Composable
fun SheetScreen(detailSeriesModel: DetailSeriesModel) {
    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by rememberSaveable { mutableStateOf(false) }
    var stateSeason by remember { mutableStateOf(detailSeriesModel.seasons[0]) }

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
        .clickable {
            isSheetOpen = true
        }) {
        Row {
            Text(
                text = stateSeason.name,
                fontWeight = FontWeight.Bold,
                color = whiteTitle,
                modifier = Modifier.weight(1f),
                fontSize = 16.sp
            )
            Icon(
                imageVector = if (isSheetOpen) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = "Arrow indicate Expandable",
                tint = whiteTitle
            )
        }
    }
    DetailSeason(detailSeriesModel, stateSeason)

    if (isSheetOpen) {
        ScreenWithBottomSheet(sheetState, onDismissRequest = { isSheetOpen = false }) {
            LazyColumn {
                items(detailSeriesModel.seasons) { season ->
                    Box(modifier = Modifier
                        .clickable {
                            stateSeason = season
                            isSheetOpen = false
                        }
                        .fillMaxWidth()) {
                        Row {
                            Text(
                                text = season.name,
                                color = if (stateSeason.id == season.id) whiteTitle else grayBodyText,
                                fontSize = 16.sp,
                                modifier = Modifier
                                    .padding(15.dp)
                                    .weight(1f)
                            )
                            Icon(
                                imageVector = Icons.Default.Done,
                                modifier = Modifier
                                    .alpha(if (stateSeason.id == season.id) 1f else 0f)
                                    .align(Alignment.CenterVertically)
                                    .padding(end = 16.dp),
                                contentDescription = "Arrow indicate Expandable",
                                tint = whiteTitle
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DetailSeason(detailSeriesModel: DetailSeriesModel, stateSeason: Seasons) {
    val detailViewModel: DetailViewModel = hiltViewModel()
    val detailSeasonState by detailViewModel.detailSeason.collectAsState()
    LaunchedEffect(detailSeriesModel.id, stateSeason.season_number) {
        detailViewModel.getDetailSeasonSeriesVM(
            detailSeriesModel.id, stateSeason.season_number
        )
    }
    when (detailSeasonState) {
        is DetailSeasonSeriesState.Error -> {}
        DetailSeasonSeriesState.Loading -> {}
        is DetailSeasonSeriesState.Success -> {
            val seasonDetail =
                (detailSeasonState as DetailSeasonSeriesState.Success).detailSeasonSeriesModel
            seasonDetail.episodes.map {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        AsyncImage(
                            model = "${BuildConfig.IMAGE_BASE_URL}${it.still_path}",
                            contentDescription = "Image Season",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(220.dp)
                        )
                        Text(
                            text = "${it.episode_number}. ${it.name}",
                            color = grayBodyText,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        val (hours, minutes) = convertMinutesToHoursAndMinutes(it.runtime)
                        val res = if (hours == 0) "" else "$hours HR "
                        Text(
                            text = "$res $minutes MIN", color = seasonRuntime, fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SeriesBasicInfo(detailSeriesModel: DetailSeriesModel) {
    Spacer(modifier = Modifier.height(15.dp))
    Text(
        text = detailSeriesModel.name,
        color = grayBodyText,
        fontSize = 23.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .fillMaxWidth()
    )
    Text(
        text = detailSeriesModel.overview,
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

    ) {
        detailSeriesModel.genres.map {
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
}


@Composable
fun ScreenWithBottomSheet(
    sheetState: SheetState, onDismissRequest: () -> Unit, content: @Composable () -> Unit
) {
    ModalBottomSheet(sheetState = sheetState,
        onDismissRequest = { onDismissRequest() },
        shape = RoundedCornerShape(5.dp),
        contentColor = background_second,
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(top = 5.dp)
                    .height(4.dp)
                    .width(50.dp)
                    .background(Color.Gray, RoundedCornerShape(2.dp))
            )
        }) {
        Text(
            text = "Temporadas",
            color = grayBodyText,
            fontSize = 14.sp,
            modifier = Modifier.padding(15.dp),
            fontWeight = FontWeight.Bold
        )
        content()
    }
}

fun round(value: Double, decimals: Int): Double {
    val factor = 10.0.pow(decimals)
    return kotlin.math.round(value * factor) / factor
}