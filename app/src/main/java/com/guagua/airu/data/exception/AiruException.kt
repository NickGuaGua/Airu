package com.guagua.airu.data.exception

sealed class AiruException(open val code: Int, override val message: String): Exception(message) {
    object General : AiruException(1001000, "something went wrong")
    object NullBody : AiruException(1001001, "response body is empty")
    class ApiResponseError(code: Int? = null, message: String?) : AiruException(code ?: 1001002, message ?: General.message)
    object Timeout : AiruException(1001002, "request timeout")
    object NetworkError : AiruException(1001002, "network error")
}