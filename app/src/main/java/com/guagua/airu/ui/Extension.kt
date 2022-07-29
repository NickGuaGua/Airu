package com.guagua.airu.ui

import com.guagua.airu.data.model.AQIStatus
import com.guagua.airu.ui.theme.AQIStatusColor

fun AQIStatus.getColor() = when (this) {
    AQIStatus.GOOD -> AQIStatusColor.GOOD
    AQIStatus.MODERATE -> AQIStatusColor.MODERATE
    AQIStatus.UNHEALTHY_FOR_SENSITIVE_GROUPS -> AQIStatusColor.UNHEALTHY_FOR_SENSITIVE_GROUPS
    AQIStatus.UNHEALTHY -> AQIStatusColor.VERY_UNHEALTHY
    AQIStatus.VERY_UNHEALTHY -> AQIStatusColor.UNHEALTHY
    AQIStatus.HAZARDOUS -> AQIStatusColor.HAZARDOUS
    AQIStatus.UNKNOWN -> AQIStatusColor.UNKNOWN
}