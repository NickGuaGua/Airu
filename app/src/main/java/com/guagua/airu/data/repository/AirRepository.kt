package com.guagua.airu.data.repository

import com.guagua.airu.data.exception.AiruException
import com.guagua.airu.data.model.AQI
import com.guagua.airu.data.model.ApiResponse
import com.guagua.airu.data.model.map
import com.guagua.epa.EpaDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

interface AirRepository {
    suspend fun getAQI(offset: Int?, limit: Int?, apiKey: String): ApiResponse<List<AQI>>
}

class AirRepositoryImpl(
    private val epaDataSource: EpaDataSource
) : AirRepository {

    override suspend fun getAQI(offset: Int?, limit: Int?, apiKey: String) = execute {
        epaDataSource.getAQI(offset, limit, apiKey)
    }.let { response ->
        ApiResponse.create(response).map { aqiList ->
            aqiList.map { AQI.create(it) }
        }
    }

    private suspend fun <T> execute(request: suspend () -> Response<T>) = withContext(Dispatchers.IO) {
        try {
            val response = request.invoke()
            if (!response.isSuccessful) {
                val errorMessage = response.errorBody()?.string()
                error(AiruException.ApiResponseError(message = errorMessage))
            }

            return@withContext response.body() ?: error(AiruException.NullBody)
        } catch (e: SocketTimeoutException) {
            throw AiruException.Timeout
        } catch (e: IOException) {
            throw AiruException.NetworkError
        } catch (e: Exception) {
            throw AiruException.General
        }
    }
}