package com.kukis.movieapp.movie.domain

import com.kukis.movieapp.movie.domain.model.MovieModel

interface MovieRepository {
    suspend fun getNowPlaying(): MovieModel?
    suspend fun getPopular(): MovieModel?
    suspend fun getTopRated(): MovieModel?
    suspend fun getUpcoming(): MovieModel?
}