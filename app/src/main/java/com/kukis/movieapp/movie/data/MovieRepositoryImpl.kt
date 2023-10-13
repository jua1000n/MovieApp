package com.kukis.movieapp.movie.data

import android.util.Log
import com.kukis.movieapp.movie.data.network.MovieClient
import com.kukis.movieapp.movie.domain.MovieRepository
import com.kukis.movieapp.movie.domain.model.MovieModel
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val apiService: MovieClient) : MovieRepository {
    override suspend fun getNowPlaying(): MovieModel? {
        kotlin.runCatching { apiService.getNowPlaying() }.onSuccess { return it.toDomain() }
            .onFailure { Log.i("Retrofit", "Error al traer el getNowPlaying ${it.message}") }
        return null
    }

    override suspend fun getPopular(): MovieModel? {
        kotlin.runCatching { apiService.getPopular() }.onSuccess { return it.toDomain() }
            .onFailure { Log.i("Retrofit", "Error al traer el getPopular ${it.message}") }
        return null
    }

    override suspend fun getTopRated(): MovieModel? {
        kotlin.runCatching { apiService.getTopRated() }.onSuccess { return it.toDomain() }
            .onFailure { Log.i("Retrofit", "Error al traer el getTopRated ${it.message}") }
        return null
    }

    override suspend fun getUpcoming(): MovieModel? {
        kotlin.runCatching { apiService.getUpcoming() }.onSuccess { return it.toDomain() }
            .onFailure { Log.i("Retrofit", "Error al traer el getUpcoming ${it.message}") }
        return null
    }
}