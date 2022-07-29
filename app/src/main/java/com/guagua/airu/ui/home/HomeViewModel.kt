package com.guagua.airu.ui.home

import com.guagua.airu.ui.base.BaseViewModel
import com.guagua.airu.usecase.GetAQIsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAQIsUseCase: GetAQIsUseCase
): BaseViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    override fun handleException(e: Throwable) {
        _state.update { it.copy(error = e, isLoading = false, isRefreshing = false) }
    }

    override fun errorConsumed(e: Throwable) {
        if (e == _state.value.error) {
            _state.update { it.copy(error = null) }
        }
    }

    fun getAQIs(isRefresh: Boolean = false) = launch {
        _state.update { it.copy(isLoading = true, isRefreshing = isRefresh, error = null) }
        val response = getAQIsUseCase.invoke(forceUpdate = isRefresh)
        val (serverAQIs, normalAQIs) = response.data.partition { it.pm2_5 >= PM2_5_THRESHOLD }
        _state.update {
            it.copy(
                isLoading = false,
                isRefreshing = false,
                severeAQIs = serverAQIs,
                normalAQIs = normalAQIs,
                error = null
            )
        }
    }

    companion object {
        private const val PM2_5_THRESHOLD = 15
    }
}