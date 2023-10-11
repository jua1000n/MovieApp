package com.kukis.movieapp.home.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kukis.movieapp.home.domain.GetTrendingAllCase
import com.kukis.movieapp.home.domain.GetTrendingMoviesCase
import com.kukis.movieapp.home.ui.model.TrendingModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTrendingAllCase: GetTrendingAllCase,
    private val getTrendingMoviesCase: GetTrendingMoviesCase
) : ViewModel() {
    private val _uiState = mutableStateOf(CombinedUiState())
    val uiState: State<CombinedUiState> = _uiState

    init {
        loadTrendingData()
    }

    private fun loadTrendingData() {
        viewModelScope.launch {
            try {
                val trendingData = getTrendingAllCase()
                _uiState.value = _uiState.value.copy(generalState = HomeUiState.Success(trendingData))
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(generalState = HomeUiState.Error(e))
            }
        }
        viewModelScope.launch {
            try {
                val trendingData = getTrendingMoviesCase()
                _uiState.value = _uiState.value.copy(moviesState = HomeUiState.Success(trendingData))
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(moviesState = HomeUiState.Error(e))
            }
        }
    }
}