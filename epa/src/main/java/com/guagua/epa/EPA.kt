package com.guagua.epa

import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

object EPA {

    private const val host = "https://data.epa.gov.tw/api/"
    private const val cacheSize = 10 * 1024 * 1024L // 10MB
    private const val cachePath = "http-cache"

    fun getEpaDataSource(
        apiKey: String,
        isDebug: Boolean,
        cacheDir: File?
    ): EpaDataSource {
        val okHttpClient = provideOkHttpClient(cacheDir, isDebug)
        val retrofit = provideRetrofit(okHttpClient)
        val epaService = retrofit.create(EpaService::class.java)
        return EpaDataSourceImpl(epaService, apiKey)
    }

    private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(host)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    private fun provideOkHttpClient(
        cacheDir: File?, isDebug: Boolean
    ): OkHttpClient = OkHttpClient.Builder().apply {
        addNetworkInterceptor(HttpLoggingInterceptor().apply {
            level = if (isDebug) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        })
        cacheDir?.let {
            val cacheDirectory = File(it, cachePath)
            cache(Cache(cacheDirectory, cacheSize))
        }
    }.build()
}