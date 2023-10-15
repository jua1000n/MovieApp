package com.kukis.movieapp.series.data.network

import android.util.Log
import com.kukis.movieapp.series.domain.SeriesRepository
import com.kukis.movieapp.series.domain.model.SeriesModel
import javax.inject.Inject

class SeriesRepositoryImpl @Inject constructor(private val apiService: SeriesClient) :SeriesRepository {
    override suspend fun getAiringSeries(): SeriesModel? {
        kotlin.runCatching { apiService.getAiringSeries() }.onSuccess { return it.toDomain() }
            .onFailure { Log.i("Retrofit", "Error al traer el getAiringSeries ${it.message}") }
        return null
    }

    override suspend fun getPopularSeries(): SeriesModel? {
        kotlin.runCatching { apiService.getPopularSeries() }.onSuccess { return it.toDomain() }
            .onFailure { Log.i("Retrofit", "Error al traer el getPopularSeries ${it.message}") }
        return null
    }

    override suspend fun getTopRatedSeries(): SeriesModel? {
        kotlin.runCatching { apiService.getTopRatedSeries() }.onSuccess { return it.toDomain() }
            .onFailure { Log.i("Retrofit", "Error al traer el getTopRatedSeries ${it.message}") }
        return null
    }

    override suspend fun getOnTheAirSeries(): SeriesModel? {
        kotlin.runCatching { apiService.getOnTheAirSeries() }.onSuccess { return it.toDomain() }
            .onFailure { Log.i("Retrofit", "Error al traer el getOnTheAirSeries ${it.message}") }
        return null
    }
}