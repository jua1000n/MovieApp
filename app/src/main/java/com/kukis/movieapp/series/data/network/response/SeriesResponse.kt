package com.kukis.movieapp.series.data.network.response

import com.google.gson.annotations.SerializedName
import com.kukis.movieapp.series.domain.model.SeriesModel

data class SeriesResponse(
    @SerializedName("page") val page: Int?,
    @SerializedName("results") val results: List<SeriesResultResponse?>?,
    @SerializedName("total_pages") val total_pages: Int?,
    @SerializedName("total_results") val total_results: Int?
) {
    fun toDomain(): SeriesModel {
        return SeriesModel(
            page = page,
            results = results?.map { it?.toDomain() },
            totalPages = total_pages,
            totalResults = total_results
        )
    }
}