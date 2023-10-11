package com.kukis.movieapp.navigation.ui.bar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Movie
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Tv
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.kukis.movieapp.navigation.ui.model.Routes

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