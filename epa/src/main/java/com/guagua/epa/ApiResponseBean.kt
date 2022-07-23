package com.guagua.epa

import com.google.gson.annotations.SerializedName

data class ApiResponseBean<T>(
    @SerializedName("total")
    val total: Int,
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("records")
    val data: T
)