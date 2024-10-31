package com.standings.data.di

import com.standings.data.repository.StandingRepositoryImp
import com.standings.data.service.StandingService
import com.standings.domain.repository.StandingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StandingDataModule {

    @Provides
    @Singleton
    fun provideRepository(service: StandingService): StandingRepository {
        return StandingRepositoryImp(service)
    }

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): StandingService {
        return retrofit.create(StandingService::class.java)
    }
}