package com.guagua.airu.data.model

enum class AQIStatus(val key: String) {
    GOOD("良好"), MODERATE("普通"), UNHEALTHY_FOR_SENSITIVE_GROUPS("對敏感族群不健康"),
    UNHEALTHY("對所有族群不健康"), VERY_UNHEALTHY("非常不健康"), HAZARDOUS("危害"), UNKNOWN("未知");

    companion object {
        private val map = values().associateBy { it.key }
        fun from(key: String) = map[key] ?: UNKNOWN
    }

    fun isGood() = this == GOOD
}