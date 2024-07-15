package com.annguyenhoang.tpiassignment.models.remote.responses

import com.google.gson.annotations.SerializedName

data class AttractionsResponse(
    @SerializedName("total")
    val total: Int? = null,
    @SerializedName("data")
    val data: List<AttractionResponse>? = null
)