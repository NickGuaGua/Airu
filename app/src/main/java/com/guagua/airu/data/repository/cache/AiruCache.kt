package com.guagua.airu.data.repository.cache

interface AiruCache {
    fun set(result: Any, vararg query: Any?)
    fun <T> get(vararg query: Any?): T?
    fun setTimeout(timeout: Long)
}

class AiruCacheImpl : AiruCache {

    private val cacheDataMap = mutableMapOf<String, Any>()
    private val cacheTimeMap = mutableMapOf<String, Long>()
    private var timeout = CACHE_TIMEOUT

    override fun set(result: Any, vararg query: Any?) {
        var queryString = ""
        query.forEach { queryString += "${it.toString()}, " }
        cacheDataMap[queryString] = result
        cacheTimeMap[queryString] = System.currentTimeMillis()
    }

    override fun <T> get(vararg query: Any?): T? {
        var queryString = ""
        query.forEach { queryString += "${it.toString()}, " }
        return (cacheDataMap[queryString] as? T)?.takeIf {
            System.currentTimeMillis() - (cacheTimeMap[queryString] ?: 0L) < timeout
        }.also {
            if (it == null) {
                cacheDataMap.remove(queryString)
                cacheTimeMap.remove(queryString)
            }
        }
    }

    override fun setTimeout(timeout: Long) {
        this.timeout = timeout
    }

    companion object {
        private const val CACHE_TIMEOUT = 5 * 60 * 1000L // 5 minutes
    }
}