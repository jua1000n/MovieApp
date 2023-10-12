package com.kukis.movieapp.search.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    val search = _search

    private val _searchUiState = MutableStateFlow<SearchUiState>(SearchUiState.Loading)
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

    fun setSearch(search: String) {
        _search.value = search
    }

    fun getSearch(query: String) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                getSearchMulti(query)
            }
            if (result != null) {
                _searchUiState.value = SearchUiState.Success(result)
            } else {
                _searchUiState.value =
                    SearchUiState.Error("Ha ocurrido un error, intentelo mas tarde")
            }
        }
    }
}