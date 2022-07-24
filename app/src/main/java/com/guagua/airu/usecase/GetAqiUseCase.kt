package com.guagua.airu.usecase

import com.guagua.airu.data.repository.AirRepository
import javax.inject.Inject

class GetAqiUseCase @Inject constructor(
    private val repository: AirRepository
) {
    suspend operator fun invoke(offset: Int?, limit: Int?) {
        repository.getAQI(offset, limit)
    }
}