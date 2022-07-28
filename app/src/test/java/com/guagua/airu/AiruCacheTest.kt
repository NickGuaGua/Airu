package com.guagua.airu

import com.guagua.airu.data.repository.cache.AiruCache
import com.guagua.airu.data.repository.cache.AiruCacheImpl
import org.junit.Test

class AiruCacheTest {

    private val airuCache: AiruCache = AiruCacheImpl()

    @Test
    fun `test cache`() {
        // GIVEN
        val params = arrayOf("param1", "param2", "param3")
        val response = "response"
        airuCache.set(response, params)

        // WHEN
        val responseFromCache = airuCache.get<String>(params)

        // THEN
        assert(responseFromCache == response)
    }
}