package com.guagua.epa

import com.guagua.epa.bean.AQIBean
import com.guagua.epa.bean.ApiResponseBean
import retrofit2.Response

interface EpaDataSource {
    suspend fun getAQI(
        offset: Int?,
        limit: Int?
    ): Response<ApiResponseBean<List<AQIBean>>>
}

internal class EpaDataSourceImpl(
    private val epaService: EpaService,
    private val apiKey: String
): EpaDataSource {

    override suspend fun getAQI(
        offset: Int?,
        limit: Int?,
    ): Response<ApiResponseBean<List<AQIBean>>> = epaService.getAQI(offset, limit, apiKey)
}