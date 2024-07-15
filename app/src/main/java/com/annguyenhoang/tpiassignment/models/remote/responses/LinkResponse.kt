package com.annguyenhoang.tpiassignment.models.remote.responses

import com.google.gson.annotations.SerializedName

data class LinkResponse(
    @SerializedName("src")
    val src: String? = null,
    @SerializedName("subject")
    val subject: String? = null
)