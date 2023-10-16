package com.kukis.movieapp.search.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.kukis.movieapp.BuildConfig
import com.kukis.movieapp.navigation.ui.model.Routes
import com.kukis.movieapp.search.domain.model.ResultModel
import com.kukis.movieapp.search.domain.model.SearchContentModel
import com.kukis.movieapp.ui.theme.grayBodyText

@Composable
fun SearchScreen(navController: NavHostController) {
    val searchViewModel: SearchViewModel = hiltViewModel()
    val search by searchViewModel.search.collectAsState()
    val searchUiState by searchViewModel.searchUiState.collectAsState()
    val listState = rememberLazyListState()

    LazyColumn(state = listState) {
        item {
            BarSearch(search) {
                searchViewModel.setSearch(it)
            }
        }
        resultSearch(searchUiState) {
            it.results?.let { it1 ->
                items(it1.chunked(2)) { pair ->
                    LaunchedEffect(listState) {
                        snapshotFlow { listState.isScrolledToEnd() }.collect { endReached ->
                            if (endReached && it.totalPages != searchUiState.currentPage) {
                                searchViewModel.setupSearchFlowPage()
                            }
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        pair.forEach { item ->
                            if (item != null) {
                                ItemSearch(item, Modifier.weight(1f), navController)
                            }
                        }
                    }
                }
            }
        }
    }
}

fun LazyListState.isScrolledToEnd() = layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1


fun resultSearch(
    searchUiState: SearchUiState, content: (SearchContentModel) -> Unit
) = when (searchUiState) {
    is SearchUiState.Error -> {}
    is SearchUiState.Success -> {
        val searchResult = searchUiState.searchContent
        content(searchResult)
    }
    is SearchUiState.Loading -> {}
}

@Composable
fun ItemSearch(item: ResultModel, modifier: Modifier, navController: NavHostController) {
    Column(modifier = modifier
        .padding(8.dp)
        .clickable {
            when (item.mediaType) {
                "movie" -> {
                    navController.navigate(Routes.MovieDetails.withId(item.id.toString()))
                }

                "tv" -> {
                    navController.navigate(Routes.SeriesDetails.withId(item.id.toString()))
                }
            }
        }) {
        AsyncImage(
            model = "${BuildConfig.IMAGE_BASE_URL}${item.posterPath}",
            contentDescription = "ImageSearch${item.originalName}",
            modifier = Modifier.height(273.dp)
        )
        (item.title ?: item.name)?.let { it1 ->
            Text(
                text = it1,
                color = grayBodyText,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                maxLines = 2
            )
        }
    }
}

@Composable
fun BarSearch(search: String, onValueChange: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(44.dp)
        )
        TextField(value = search,
            placeholder = { Text(text = "Qué estas buscando?") },
            leadingIcon = {
                if (search == "") {
                    Icon(
                        imageVector = Icons.Default.Search, contentDescription = ""
                    )
                } else {
                    Icon(imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = "",
                        modifier = Modifier.clickable { onValueChange("") })
                }
            },
            onValueChange = { onValueChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
    }
}
