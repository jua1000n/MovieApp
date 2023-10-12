package com.kukis.movieapp.details.data

import android.util.Log
import com.kukis.movieapp.details.data.network.DetailClient
import com.kukis.movieapp.details.domain.DetailRepository
import com.kukis.movieapp.details.domain.model.DetailMovieModel
import com.kukis.movieapp.details.domain.model.DetailSeasonSeriesModel
import com.kukis.movieapp.details.domain.model.DetailSeriesModel
import javax.inject.Inject

class DetailRepositoryImpl @Inject constructor(private val apiService: DetailClient) :
    DetailRepository {
    override suspend fun getDetailMovie(id: Int): DetailMovieModel? {
        kotlin.runCatching { apiService.getDetailMovie(id) }.onSuccess { return it.toDomain() }
            .onFailure { Log.i("Retrofit", "Error al traer el getDetailMovie ${it.message}") }
        return null
    }

    override suspend fun getDetailSeries(id: Int): DetailSeriesModel? {
        kotlin.runCatching { apiService.getDetailSeries(id) }.onSuccess { return it.toDomain() }
            .onFailure { Log.i("Retrofit", "Error al traer el getDetailSeries ${it.message}") }
        return null
    }

    override suspend fun getDetailSeasonSeries(id: Int, season: Int): DetailSeasonSeriesModel? {
        kotlin.runCatching { apiService.getDetailSeasonSeries(id, season) }
            .onSuccess { return it.toDomain() }.onFailure {
                Log.i(
                    "Retrofit", "Error al traer el getDetailSeasonSeries ${it.message}"
                )
            }
        return null
    }

}