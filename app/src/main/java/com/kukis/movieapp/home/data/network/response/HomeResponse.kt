package com.kukis.movieapp.home.data.network.response

import com.google.gson.annotations.SerializedName
import com.kukis.movieapp.home.ui.model.TrendingModel

data class HomeResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<TrendingModel>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)
