package com.annguyenhoang.tpiassignment.core.network

import androidx.annotation.StringRes
import com.annguyenhoang.tpiassignment.R

sealed class ApiException : Exception() {
    data class ServerError(
        val detail: String? = null,
        val code: String? = null,
        val source: String? = null
    ) : ApiException()

    data object BadRequest : ApiException() {
        private fun readResolve(): Any = BadRequest
    }

    data object NotFound : ApiException() {
        private fun readResolve(): Any = NotFound
    }

    data object Forbidden : ApiException() {
        private fun readResolve(): Any = Forbidden
    }

    data object InternalServerError : ApiException() {
        private fun readResolve(): Any = InternalServerError
    }

    data object TooManyRequests : ApiException() {
        private fun readResolve(): Any = TooManyRequests
    }

    data object UnAuthorized : ApiException() {
        private fun readResolve(): Any = UnAuthorized
    }

    data object Conflict : ApiException() {
        private fun readResolve(): Any = Conflict
    }

    data object GatewayTimeout : ApiException() {
        private fun readResolve(): Any = GatewayTimeout
    }

    data object MethodNotAllow : ApiException() {
        private fun readResolve(): Any = MethodNotAllow
    }

    data object BadGateway : ApiException() {
        private fun readResolve(): Any = BadGateway
    }

    data object ServiceUnavailable : ApiException() {
        private fun readResolve(): Any = ServiceUnavailable
    }

    data object NoInternetConnection : ApiException() {
        private fun readResolve(): Any = ServiceUnavailable
    }

    data object Other : ApiException() {
        private fun readResolve(): Any = Other
    }

    companion object {
        fun findApiExceptionBasedStatusCode(statusCode: Int?): ApiException {
            val code = statusCode ?: -1
            return when (code) {
                400 -> BadRequest
                404 -> NotFound
                403 -> Forbidden
                500 -> InternalServerError
                429 -> TooManyRequests
                401 -> UnAuthorized
                409 -> Conflict
                408 -> GatewayTimeout
                405 -> MethodNotAllow
                502 -> BadGateway
                503 -> ServiceUnavailable
                else -> Other
            }
        }
    }
}

@StringRes
fun ApiException.getStringResFromException(): Int {
    return when (this) {
        ApiException.BadGateway -> R.string.bad_gateway_error
        ApiException.BadRequest -> R.string.bad_request_error
        ApiException.Conflict -> R.string.conflict_error
        ApiException.Forbidden -> R.string.forbidden_error
        ApiException.GatewayTimeout -> R.string.gateway_timeout_error
        ApiException.InternalServerError -> R.string.internal_server_error
        ApiException.MethodNotAllow -> R.string.method_not_allow_error
        ApiException.NotFound -> R.string.not_found_error
        ApiException.ServiceUnavailable -> R.string.service_unavailable_error
        ApiException.TooManyRequests -> R.string.too_many_requests_error
        ApiException.UnAuthorized -> R.string.unauthorized_error
        ApiException.NoInternetConnection -> R.string.no_internet_connection_error
        else -> R.string.other_error
    }
}