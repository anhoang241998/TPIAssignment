package com.annguyenhoang.tpiassignment.core.network

import com.annguyenhoang.tpiassignment.utils.AppLogHelper
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okio.Buffer
import java.io.IOException
import java.nio.charset.StandardCharsets

class NetworkLoggingInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        AppLogHelper.logDebug(buildRequestLog(request))
        AppLogHelper.logDebug(buildResponseLog(response))

        return response
    }

    private fun buildRequestLog(request: Request): String {
        val logBuilder = StringBuilder()
        logBuilder.apply {
            append("Request URL: ${request.url}\n")
            append("Request Method: ${request.method}\n")

            val headers = request.headers.names()
            if (headers.isNotEmpty()) {
                append("Headers: \n")
                headers.forEach { header ->
                    logBuilder.append("\t$header: ${request.headers[header]}\n")
                }
            }

            request.body?.let { requestBody ->
                val buffer = Buffer()
                requestBody.writeTo(buffer)

                val contentType = requestBody.contentType()
                val charset = contentType?.charset(StandardCharsets.UTF_8) ?: StandardCharsets.UTF_8

                val isJson = contentType?.toString()?.contains("application/json") == true
                if (isJson) {
                    val gson = GsonBuilder()
                        .setPrettyPrinting()
                        .create()
                    val jsonBody = buffer.readString(charset)
                    // Convert raw JSON string to a pretty-printed format
                    val jsonElement = gson.fromJson(jsonBody, JsonElement::class.java)
                    val prettyJson = gson.toJson(jsonElement)
                    append("Request Body: \n$prettyJson\n")
                } else {
                    append("Request Body: \n")
                    append("\t${buffer.readString(charset)}\n")
                }
            }
        }

        return logBuilder.toString()
    }

    private fun buildResponseLog(response: Response): String {
        val responseBody = response.peekBody(Long.MAX_VALUE)
        val logBuilder = StringBuilder()
        logBuilder.apply {
            logBuilder.append("Response URL: ${response.request.url}\n")
            logBuilder.append("Status Code: ${response.code}\n")

            val contentType = responseBody.contentType()
            val isJson = contentType?.toString()?.contains("application/json") == true

            if (isJson) {
                val gson = GsonBuilder()
                    .setPrettyPrinting()
                    .create()
                val jsonBody = responseBody.string()
                val jsonElement = gson.fromJson(jsonBody, JsonElement::class.java)
                val prettyJson = gson.toJson(jsonElement)
                logBuilder.append("Response Body:\n$prettyJson\n")
            } else {
                logBuilder.append("Response Body: \n${responseBody.string()}\n")
            }
        }

        return logBuilder.toString()
    }

}