package com.kukis.movieapp.search.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kukis.movieapp.search.domain.model.ResultModel
import com.kukis.movieapp.search.domain.usecase.GetSearchMulti
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchMulti: GetSearchMulti
) : ViewModel() {
    private val _search = MutableStateFlow<String>("")


    private val _searchUiState = MutableStateFlow<SearchUiState>(SearchUiState.Loading())
    val searchUiState = _searchUiState

    init {
        setupSearchFlow()
    }

    private fun setupSearchFlow() {
        viewModelScope.launch {
            _search.debounce(300).filter { it.isNotBlank() }.distinctUntilChanged()
                .collect { query ->
                    getSearch(query)
                }
        }
    }

    fun setupSearchFlowPage() {
        viewModelScope.launch {
            _search.debounce(300).filter { it.isNotBlank() }.distinctUntilChanged()
                .collect { query ->
                    getSearchPage(query)
                }
        }
    }

    fun setSearch(search: String) {
        _search.value = search
        SearchUiState.Loading(1)
    }

    private fun getSearch(query: String) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                getSearchMulti(query, 1)
            }
            if (result != null) {
                _searchUiState.value = SearchUiState.Success(result, 1)
            } else {
                _searchUiState.value =
                    SearchUiState.Error("Ha ocurrido un error, intentelo mas tarde")
            }
        }
    }

    private fun getSearchPage(query: String) {
        val currentState = _searchUiState.value
        var currentPage = when (currentState) {
            is SearchUiState.Loading -> currentState.currentPage
            is SearchUiState.Error -> currentState.currentPage
            is SearchUiState.Success -> currentState.currentPage
        }
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                getSearchMulti(query, currentPage)
            }
            if (result != null) {
                if (currentPage < result.totalPages) currentPage += 1
                val combinedResults: List<ResultModel?>? =
                    if (currentState is SearchUiState.Success) {
                        if (result.totalPages > currentPage) {
                            val currentResults = currentState.searchContent.results ?: emptyList()
                            val newResults = result.results ?: emptyList()
                            currentResults + newResults
                        } else currentState.searchContent.results
                    } else {
                        result.results
                    }
                val combinedSearchContent = result.copy(results = combinedResults)
                if (result.results?.isNotEmpty()!!) _searchUiState.value =
                    SearchUiState.Success(combinedSearchContent, currentPage = currentPage)
            } else {
                _searchUiState.value =
                    SearchUiState.Error("Ha ocurrido un error, intentelo mas tarde")
            }
        }
    }
}
