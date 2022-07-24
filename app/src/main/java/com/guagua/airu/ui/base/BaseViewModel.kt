package com.guagua.airu.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel: ViewModel() {

    protected open fun handleException(e: Throwable) {
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        handleException(throwable)
    }

    protected fun launch(block: suspend () -> Unit) =
        viewModelScope.launch(exceptionHandler) { block() }
}