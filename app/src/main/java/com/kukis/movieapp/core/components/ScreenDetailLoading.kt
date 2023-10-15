package com.kukis.movieapp.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kukis.movieapp.ui.theme.background_second

@Preview(showBackground = true)
@Composable
fun ScreenDetailLoading() {
    //HeaderDetail()
    LazyColumn(modifier = Modifier.background(Color.Black)) {
        item {
            HeaderDetail()
        }
        item {
            BodyDetail()
        }
    }
}

@Composable
fun BodyDetail() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .height(23.dp)
                .width(250.dp)
                .background(background_second,RoundedCornerShape(15.dp))
        )
        repeat(5) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 4.dp)
                    .height(13.dp)
                    .fillMaxWidth()
                    .background(background_second,RoundedCornerShape(15.dp))
            )
        }
    }
}

@Composable
fun HeaderDetail() {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    Column() {
        Box(
            modifier = Modifier
                .height(670.dp)
                .fillMaxWidth()
                .clipToBounds()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.BottomCenter)
                    .background(background_second)
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
                    Box(
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .clip(RoundedCornerShape(15.dp))
                            .background(background_second)
                            .width(64.dp)
                            .height(16.dp)
                    )
                    Box(
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .clip(RoundedCornerShape(15.dp))
                            .background(background_second)
                            .width(64.dp)
                            .height(16.dp)
                    )
                }
            }
        }
    }
}
