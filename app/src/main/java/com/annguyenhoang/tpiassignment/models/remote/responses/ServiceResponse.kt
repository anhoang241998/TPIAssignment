package com.annguyenhoang.tpiassignment.models.remote.responses

import com.google.gson.annotations.SerializedName

data class ServiceResponse(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
)
