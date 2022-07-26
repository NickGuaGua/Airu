package com.guagua.airu.ui.home

import com.guagua.airu.data.model.AQI

data class HomeState(
    val isLoading: Boolean = false,
    val isRefresh: Boolean = false,
    val error: Throwable? = null,
    val severeAQIs: List<AQI>? = null,
    val normalAQIs: List<AQI>? = null
)