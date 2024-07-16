package com.annguyenhoang.tpiassignment.repositories

import com.annguyenhoang.tpiassignment.core.network.ApiResponseHandler
import com.annguyenhoang.tpiassignment.models.remote.AttractionsApi
import com.annguyenhoang.tpiassignment.models.remote.responses.AttractionsResponse

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
    ): Result<AttractionsResponse>? {
        var result: Result<AttractionsResponse>? = null

        apiResponseHandler.safeCall(
            request = { api.getAllAttractions(lang, categoryIds, nlat, elong, page) },
            onRequestSuccess = { response ->
                result = Result.success(response)
            },
            onRequestFailed = { e ->
                result = Result.failure(e)
            }
        )

        return result
    }

}