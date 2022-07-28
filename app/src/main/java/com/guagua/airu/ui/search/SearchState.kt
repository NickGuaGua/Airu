package com.guagua.airu.ui.search

import com.guagua.airu.data.model.AQI

data class SearchState(
    val isLoading: Boolean = false,
    val error: Throwable? = null,
    val searchResult: List<AQI> = emptyList()
)