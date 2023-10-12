package com.kukis.movieapp.navigation.ui.bar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Cast
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.PersonOutline
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.kukis.movieapp.R
import com.kukis.movieapp.navigation.ui.model.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavHostController, currentRoute: String, title: @Composable () -> Unit) {
    CenterAlignedTopAppBar(title = { title() },
        navigationIcon = {
            IconButton(onClick = {
                //Create lateral navigation
            }) {
                if (currentRoute == Routes.MovieDetails.route || currentRoute == Routes.SeriesDetails.route) {
                    Icon(imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = "LateralMenuTopBar",
                        tint = Color.White,
                        modifier = Modifier.clickable {
                            navController.popBackStack()
                        })
                } else {
                    Icon(
                        imageVector = Icons.Rounded.Menu,
                        contentDescription = "LateralMenuTopBar",
                        tint = Color.White
                    )

                }
            }
        },
        actions = {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Rounded.Cast,
                    contentDescription = "CastTopBar",
                    tint = Color.White
                )
            }
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Rounded.PersonOutline,
                    contentDescription = "UserTopBar",
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(containerColor = Color.Black.copy(alpha = 0.50f))
    )
}

@Composable
fun TitleTopBar(text: String, contentDescription: String) {
    Column(
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .size(30.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Icon(painter = painterResource(id = R.drawable.hbo_max_white_logo),
                contentDescription = contentDescription,
                tint = Color.White,
                modifier = Modifier.clickable { })
        }
        Text(
            text = text, fontSize = 15.sp, color = Color.White, fontWeight = FontWeight.Bold
        )
    }
}