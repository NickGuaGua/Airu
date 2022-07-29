package com.guagua.airu.domain

import com.guagua.airu.data.repository.AirRepository
import javax.inject.Inject

class GetAQIsUseCase @Inject constructor(
    private val repository: AirRepository
) {
    suspend operator fun invoke(offset: Int = 1, limit: Int = 1000, forceUpdate: Boolean = false) =
        repository.getAQI(offset, limit, forceUpdate)
}