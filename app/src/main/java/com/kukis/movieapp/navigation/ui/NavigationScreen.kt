package com.kukis.movieapp.navigation.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Cast
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Movie
import androidx.compose.material.icons.rounded.PersonOutline
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Tv
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kukis.movieapp.R
import com.kukis.movieapp.home.ui.HomeScreen
import com.kukis.movieapp.home.ui.HomeViewModel
import com.kukis.movieapp.movie.ui.MoviesScreen
import com.kukis.movieapp.movie.ui.SearchScreen
import com.kukis.movieapp.navigation.ui.model.Routes
import com.kukis.movieapp.series.ui.SeriesScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
//@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationScreen(homeViewModel: HomeViewModel) {
    val navController = rememberNavController()
    val currentRoute = rememberSaveable { mutableStateOf(Routes.Home.route) }
    LaunchedEffect(navController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            currentRoute.value = destination.route ?: ""
        }
    }
    Scaffold(topBar = {
        TopBar {
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
            composable(Routes.Home.route) {
                HomeScreen(homeViewModel)
            }
            composable(Routes.Movies.route) {
                MoviesScreen()
            }
            composable(Routes.Series.route) {
                SeriesScreen()
            }
            composable(Routes.Search.route) {
                SearchScreen()
            }
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController, route: String) {
    val navItems = listOf(
        Pair(Routes.Home.route, Icons.Rounded.Home),
        Pair(Routes.Movies.route, Icons.Rounded.Movie),
        Pair(Routes.Series.route, Icons.Rounded.Tv),
        Pair(Routes.Search.route, Icons.Rounded.Search)
    )
    NavigationBar(
        containerColor = Color.Black.copy(alpha = 0.90f), contentColor = Color.Transparent
    ) {
        navItems.forEach { (navRoute, icon) ->
            NavigationBarItem(colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                indicatorColor = Color.Transparent,
                unselectedIconColor = Color.Gray,
                selectedTextColor = Color.Transparent,
                unselectedTextColor = Color.Transparent
            ),
                selected = route == navRoute,
                onClick = { navController.navigate(navRoute) },
                icon = {
                    Icon(
                        imageVector = icon, contentDescription = "NavigationBar$navRoute"
                    )
                })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(title: @Composable () -> Unit) {
    CenterAlignedTopAppBar(title = { title() },
        navigationIcon = {
            IconButton(onClick = {
                //Create lateral navigation
            }) {
                Icon(
                    imageVector = Icons.Rounded.Menu,
                    contentDescription = "LateralMenuTopBar",
                    tint = Color.White
                )
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
            onClick = { }, modifier = Modifier
                .fillMaxWidth()
                .size(30.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.hbo_max_white_logo),
                contentDescription = contentDescription,
                tint = Color.White,
                modifier = Modifier.clickable {  }
            )
        }
        Text(
            text = text, fontSize = 15.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun IneddHelp() {

}