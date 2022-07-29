package com.guagua.airu.data.model

import com.guagua.epa.bean.AQIBean

data class AQI(
    val siteId: String,
    val siteName: String,
    val county: String,
    val pm2_5: Int,
    val status: AQIStatus,
) {
    companion object {
        fun create(bean: AQIBean) = with(bean) {
            AQI(siteId, siteName, county, pm2_5.toIntOrNull() ?: 0, AQIStatus.from(status))
        }
    }
}