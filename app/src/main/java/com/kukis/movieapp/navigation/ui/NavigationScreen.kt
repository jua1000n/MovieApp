package com.kukis.movieapp.navigation.ui

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kukis.movieapp.details.ui.MovieDetailsScreen
import com.kukis.movieapp.details.ui.SeriesDetailsScreen
import com.kukis.movieapp.home.ui.HomeScreen
import com.kukis.movieapp.movie.ui.MoviesScreen
import com.kukis.movieapp.navigation.ui.bar.BottomBar
import com.kukis.movieapp.navigation.ui.bar.TitleTopBar
import com.kukis.movieapp.navigation.ui.bar.TopBar
import com.kukis.movieapp.navigation.ui.model.Routes
import com.kukis.movieapp.search.ui.SearchScreen
import com.kukis.movieapp.series.ui.SeriesScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavigationScreen() {
    val navController = rememberNavController()
    val currentRoute = rememberSaveable { mutableStateOf(Routes.Home.route) }
    LaunchedEffect(navController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            currentRoute.value = destination.route ?: ""
        }
    }
    Scaffold(topBar = {
        TopBar(navController, currentRoute.value) {
            when (currentRoute.value) {
                Routes.Movies.route -> {
                    TitleTopBar(text = "Movies", contentDescription = "LogoApp")
                }

                Routes.Series.route -> {
                    TitleTopBar(text = "Series", contentDescription = "LogoApp")
                }

                else -> {
                    TitleTopBar(text = "", contentDescription = "LogoApp")
                }
            }
        }
    },
        containerColor = Color.Transparent,
        bottomBar = { BottomBar(navController, currentRoute.value) }) {

        NavHost(navController = navController, startDestination = Routes.Home.route) {
            composable(
                Routes.Home.route
            ) {
                HomeScreen(navController)
            }
            composable(Routes.Movies.route) {
                MoviesScreen(navController)
            }
            composable(Routes.Series.route) {
                SeriesScreen(navController)
            }
            composable(Routes.Search.route) {
                SearchScreen(navController)
            }
            composable(
                Routes.MovieDetails.route
            ) {
                val idArg: String? = it.arguments?.getString("id")
                if (idArg != null) {
                    MovieDetailsScreen(idArg, navController)
                }
            }
            composable(Routes.SeriesDetails.route) {
                val idArg: String? = it.arguments?.getString("id")
                if (idArg != null) {
                    SeriesDetailsScreen(idArg, navController)
                }
            }
        }
    }
}