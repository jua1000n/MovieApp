package com.kukis.movieapp.movie.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController

@Composable
fun MoviesScreen(navController: NavHostController) {
    Text(text = "Movies", color = Color.White)
}