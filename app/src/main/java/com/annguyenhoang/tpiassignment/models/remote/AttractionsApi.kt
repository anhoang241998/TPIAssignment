package com.annguyenhoang.tpiassignment.models.remote

import com.annguyenhoang.tpiassignment.models.remote.responses.AttractionsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface AttractionsApi {

    @GET("Attractions/All")
    @Headers("Accept: application/json")
    suspend fun getAllAttractions(
        @Query("lang") lang: String,
        @Query("categoryIds") categoryIds: String? = null,
        @Query("nlat") nlat: Double? = null,
        @Query("elong") elong: Double? = null,
        @Query("page") page: Int? = null
    ): Response<AttractionsResponse>
}