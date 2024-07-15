package com.annguyenhoang.tpiassignment.core.network

import com.annguyenhoang.tpiassignment.core.network.ApiException.Companion.findApiExceptionBasedStatusCode
import retrofit2.Response
import kotlin.coroutines.cancellation.CancellationException

class ApiResponseHandler() {

    @Throws(CancellationException::class)
    suspend fun <T> safeCall(
        request: suspend () -> Response<T>,
        onRequestSuccess: suspend (data: T) -> Unit,
        onRequestFailed: suspend (Exception) -> Unit
    ) {
        try {
            val response = request()
            if (response.isSuccessful) {
                response.body()?.let {
                    onRequestSuccess(it)
                } ?: run {
                    onRequestFailed(ApiException.Other)
                }
            } else {
                val statusCode = response.code()
                onRequestFailed(findApiExceptionBasedStatusCode(statusCode))
            }
        } catch (e: Exception) {
            if (e == CancellationException()) {
                throw e
            }
            onRequestFailed(ApiException.Other)
        }
    }

}