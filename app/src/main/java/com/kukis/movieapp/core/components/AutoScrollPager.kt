package com.kukis.movieapp.core.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
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