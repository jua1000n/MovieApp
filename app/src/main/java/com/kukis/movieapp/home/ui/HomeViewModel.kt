package com.kukis.movieapp.home.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kukis.movieapp.home.domain.GetTrendingAllCase
import com.kukis.movieapp.home.ui.model.TrendingModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTrendingAllCase: GetTrendingAllCase
) : ViewModel() {
    private val _uiState = mutableStateOf<HomeUiState>(HomeUiState.Loading)
    val uiState: State<HomeUiState> = _uiState

    init {
        loadTrendingData()
    }

    private fun loadTrendingData() {
        viewModelScope.launch {
            try {
                val trendingData = getTrendingAllCase()
                _uiState.value = HomeUiState.Success(trendingData)
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error(e)
            }
        }
    }
}