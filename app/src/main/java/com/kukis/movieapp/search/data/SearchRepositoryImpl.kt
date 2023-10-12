package com.kukis.movieapp.search.data

import android.util.Log
import com.kukis.movieapp.search.data.network.SearchClient
import com.kukis.movieapp.search.domain.SearchRepository
import com.kukis.movieapp.search.domain.model.SearchContentModel
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(private val apiService: SearchClient) :
    SearchRepository {
    override suspend fun getSearchMulti(search: String): SearchContentModel? {
        kotlin.runCatching { apiService.getSearchMulti(search) }.onSuccess { return it.toDomain() }
            .onFailure { Log.i("Retrofit", "Error al traer el getSearchMulti ${it.message}") }
        return null
    }
}