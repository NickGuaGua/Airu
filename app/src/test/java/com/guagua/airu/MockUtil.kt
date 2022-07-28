package com.guagua.airu

import com.guagua.airu.data.model.AQI
import com.guagua.airu.data.model.AQIStatus
import com.guagua.airu.data.model.ApiResponse

object MockUtil {

    fun mockAQI(
        id: String = "1",
        name: String = "site",
        county: String = "county",
        pm2_5: Double = 1.0,
        status: AQIStatus = AQIStatus.GOOD
    ): AQI = AQI(id, name, county, pm2_5, status)

    fun mockAQIs(nums: Int): List<AQI> {
        return (1..nums).map {
            mockAQI(
                "id_$it",
                "site_$it",
                "county_$it",
                it.toDouble(),
                AQIStatus.values()[it % AQIStatus.values().size]
            )
        }
    }

    fun <T> mockAPIResponseWithListingData(data: List<T>): ApiResponse<List<T>> {
        return ApiResponse(data.size, data.size, 1, data)
    }
}