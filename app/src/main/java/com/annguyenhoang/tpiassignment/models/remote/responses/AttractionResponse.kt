package com.annguyenhoang.tpiassignment.models.remote.responses

import com.google.gson.annotations.SerializedName

data class AttractionResponse(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("name_zh")
    val nameZh: String? = null,
    @SerializedName("open_status")
    val openStatus: Int? = null,
    @SerializedName("introduction")
    val introduction: String? = null,
    @SerializedName("open_time")
    val openTime: String? = null,
    @SerializedName("zipcode")
    val zipCode: String? = null,
    @SerializedName("distric")
    val district: String? = null,
    @SerializedName("address")
    val address: String? = null,
    @SerializedName("tel")
    val telephone: String? = null,
    @SerializedName("fax")
    val fax: String? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("months")
    val months: String? = null,
    @SerializedName("nlat")
    val nLat: Double? = null,
    @SerializedName("elong")
    val eLong: Double? = null,
    @SerializedName("official_site")
    val officialSite: String? = null,
    @SerializedName("facebook")
    val faceBook: String? = null,
    @SerializedName("ticket")
    val ticket: String? = null,
    @SerializedName("remind")
    val remind: String? = null,
    @SerializedName("staytime")
    val stayTime: String? = null,
    @SerializedName("modified")
    val modified: String? = null,
    @SerializedName("url")
    val url: String? = null,
    @SerializedName("category")
    val category: List<CategoryResponse>? = null,
    @SerializedName("target")
    val target: List<TargetResponse>? = null,
    @SerializedName("service")
    val service: List<ServiceResponse>? = null,
    @SerializedName("friendly")
    val friendly: List<Any>? = null,
    @SerializedName("images")
    val image: List<ImageResponse>? = null,
    @SerializedName("files")
    val files: List<Any>? = null,
    @SerializedName("links")
    val links: List<LinkResponse>? = null
)