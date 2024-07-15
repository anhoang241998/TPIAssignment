package com.annguyenhoang.tpiassignment.repositories

import com.annguyenhoang.tpiassignment.core.network.ApiException
import com.annguyenhoang.tpiassignment.core.network.ApiResponseHandler
import com.annguyenhoang.tpiassignment.models.remote.AttractionsApi
import com.annguyenhoang.tpiassignment.models.remote.responses.AttractionResponse

class AttractionsRepository(
    private val api: AttractionsApi,
    private val apiResponseHandler: ApiResponseHandler
) {

    suspend fun getAllAttractions(
        lang: String,
        categoryIds: String? = null,
        nlat: Double? = null,
        elong: Double? = null,
        page: Int? = 1
    ): Result<List<AttractionResponse>>? {
        var result: Result<List<AttractionResponse>>? = null

        apiResponseHandler.safeCall(
            request = { api.getAllAttractions(lang, categoryIds, nlat, elong, page) },
            onRequestSuccess = { response ->
                response.data?.let { attractionsResponse ->
                    result = Result.success(attractionsResponse)
                } ?: run {
                    result = Result.failure(ApiException.Other)
                }
            },
            onRequestFailed = { e ->
                result = Result.failure(e)
            }
        )

        return result
    }

}