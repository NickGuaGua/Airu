package com.guagua.epa

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @link https://data.epa.gov.tw/swagger/#/%E5%A4%A7%E6%B0%A3/get_aqx_p_432
 */
internal interface EpaService {

    @GET("v2/aqx_p_432")
    suspend fun getAQI(
        @Query("offset") offset: Int?,
        @Query("limit") limit: Int?,
        @Query("api_key") apiKey: String
    ): Response<ApiResponseBean<List<AQIBean>>>
}