package com.guagua.airu.di

import android.content.Context
import com.guagua.airu.BuildConfig
import com.guagua.airu.data.repository.AirRepository
import com.guagua.airu.data.repository.AirRepositoryImpl
import com.guagua.airu.data.repository.cache.AiruCache
import com.guagua.airu.data.repository.cache.AiruCacheImpl
import com.guagua.epa.EPA
import com.guagua.epa.EpaDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AirModule {

    @Provides
    @Singleton
    fun provideAirRepository(epaDataSource: EpaDataSource, cache: AiruCache): AirRepository {
        return AirRepositoryImpl(epaDataSource, cache)
    }

    @Provides
    @Singleton
    fun provideEpaDataSource(@ApplicationContext context: Context): EpaDataSource {
        return EPA.getEpaDataSource(
            BuildConfig.EpaApiKey,
            BuildConfig.DEBUG,
            context.cacheDir
        )
    }

    @Provides
    @Singleton
    fun provideCache(): AiruCache {
        return AiruCacheImpl()
    }
}