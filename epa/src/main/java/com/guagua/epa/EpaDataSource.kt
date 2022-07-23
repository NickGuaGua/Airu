package com.guagua.epa

import retrofit2.Response

interface EpaDataSource {
    suspend fun getAQI(
        offset: Int?,
        limit: Int?,
        apiKey: String
    ): Response<ApiResponseBean<List<AQIBean>>>
}

internal class EpaDataSourceImpl(
    private val epaService: EpaService
): EpaDataSource {

    override suspend fun getAQI(
        offset: Int?,
        limit: Int?,
        apiKey: String
    ): Response<ApiResponseBean<List<AQIBean>>> = epaService.getAQI(offset, limit, apiKey)
}

