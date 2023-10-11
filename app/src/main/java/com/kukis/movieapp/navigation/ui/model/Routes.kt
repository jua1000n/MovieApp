package com.kukis.movieapp.navigation.ui.model

sealed class Routes(val route:String) {
    object Home : Routes("home")
    object Movies : Routes("movies")
    object Series : Routes("series")
    object Search : Routes("search")
    object MovieDetails : Routes("movieDetails/{id}") {
        fun withId(id:String) = "movieDetails/$id"
    }
    object SeriesDetails : Routes("seriesDetails/{id}") {
        fun withId(id:String) = "seriesDetails/$id"
    }

}
