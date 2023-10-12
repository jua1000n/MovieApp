package com.kukis.movieapp.search.data.network.response

import com.google.gson.annotations.SerializedName
import com.kukis.movieapp.search.domain.model.SearchContentModel

data class SearchContentResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<ResultResponse?>?,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
) {
    fun toDomain(): SearchContentModel {
        return SearchContentModel(
            page,
            results?.map { it?.toDomain() },
            totalPages,
            totalResults
        )
    }
}