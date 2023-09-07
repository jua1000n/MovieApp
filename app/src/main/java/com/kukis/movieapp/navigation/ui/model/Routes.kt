package com.kukis.movieapp.navigation.ui.model

sealed class Routes(val route:String) {
    object Home : Routes("home")
    object Movies : Routes("movies")
    object Series : Routes("series")
    object Search : Routes("search")
}
