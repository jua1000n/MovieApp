package com.kukis.movieapp.search.ui

import com.kukis.movieapp.search.domain.model.SearchContentModel

sealed class SearchUiState {
    data class Loading(override val currentPage: Int = 1) : SearchUiState()
    data class Error(val error: String, override val currentPage: Int = 1) : SearchUiState()
    data class Success(val searchContent: SearchContentModel, override val currentPage: Int = 1) :
        SearchUiState()

    abstract val currentPage: Int
}
