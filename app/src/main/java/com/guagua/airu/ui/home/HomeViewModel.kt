package com.guagua.airu.ui.home

import com.guagua.airu.ui.base.BaseViewModel
import com.guagua.airu.usecase.GetAQIsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
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
        _state.update { it.copy(error = e) }
    }

    fun getAQIs() = withLoading {
        launch {
            val response = getAQIsUseCase.invoke()
            val (serverAQIs, normalAQIs) = response.data.partition { it.pm2_5 >= PM2_5_THRESHOLD }
            _state.update { it.copy(severeAQIs = serverAQIs, normalAQIs = normalAQIs) }
        }
    }

    private fun withLoading(block: () -> Job) {
        _state.update { it.copy(isLoading = true) }
        block().invokeOnCompletion {
            _state.update { it.copy(isLoading = false) }
        }
    }

    companion object {
        private const val PM2_5_THRESHOLD = 15
    }
}