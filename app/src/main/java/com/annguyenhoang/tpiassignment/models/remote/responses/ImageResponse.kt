package com.annguyenhoang.tpiassignment.models.remote.responses

import com.google.gson.annotations.SerializedName

data class ImageResponse(
    @SerializedName("src")
    val src: String? = null,
    @SerializedName("subject")
    val subject: String? = null,
    @SerializedName("ext")
    val ext: String? = null
)