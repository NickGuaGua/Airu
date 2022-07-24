package com.guagua.airu.data.model

import com.guagua.epa.ApiResponseBean

data class ApiResponse<T>(
    val total: Int,
    val limit: Int,
    val offset: Int,
    val data: T
) {
    companion object {
        fun <T> create(bean: ApiResponseBean<T>) = with(bean) {
            ApiResponse(total, limit, offset, data)
        }
    }
}

fun <T, R> ApiResponse<T>.map(transform: (T) -> R): ApiResponse<R> {
    return ApiResponse(total, limit, offset, transform.invoke(data))
}