package com.kukis.movieapp.search.ui

import com.kukis.movieapp.search.domain.model.SearchContentModel

sealed class SearchUiState {
    object Loading : SearchUiState()
    data class Error(val error: String) : SearchUiState()
    data class Success(val searchContent: SearchContentModel) : SearchUiState()
}
