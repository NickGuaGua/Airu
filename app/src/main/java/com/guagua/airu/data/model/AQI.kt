package com.guagua.airu.data.model

import com.guagua.epa.AQIBean

data class AQI(
    val siteId: String,
    val siteName: String,
    val county: String,
    val pm2_5: Double,
    val status: AQIStatus,
) {
    companion object {
        fun create(bean: AQIBean) = with(bean) {
            AQI(siteId, siteName, county, pm2_5.toDoubleOrNull() ?: 0.0, AQIStatus.from(status))
        }
    }
}