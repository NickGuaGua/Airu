package com.guagua.airu.ui.search

import com.guagua.airu.ui.base.BaseViewModel
import com.guagua.airu.usecase.GetAQIsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getAQIsUseCase: GetAQIsUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(SearchState())
    val state = _state.asStateFlow()

    private var searchJob: Job? = null

    fun search(keyword: String) {
        searchJob?.cancel()
        searchJob = launch {
            _state.update { it.copy(isLoading = true) }
            delay(DEBOUNCE_INTERVAL)
            val searchResult = if (keyword.isEmpty()) emptyList()
            else {
                val response = getAQIsUseCase.invoke()
                response.data.filter { it.siteName.contains(keyword) || it.county.contains(keyword) }
            }
            _state.update {
                it.copy(isLoading = false, error = null, searchResult = searchResult)
            }
        }
    }

    companion object {
        private const val DEBOUNCE_INTERVAL = 750L
    }
}