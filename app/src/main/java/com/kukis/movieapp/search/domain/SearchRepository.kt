package com.kukis.movieapp.search.domain

import com.kukis.movieapp.search.domain.model.SearchContentModel

interface SearchRepository {
    suspend fun getSearchMulti(search: String): SearchContentModel?
}