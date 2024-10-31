package com.standings.domain.di

import com.standings.domain.repository.StandingRepository
import com.standings.domain.usecase.GetStandingsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StandingDomainModule {

    @Provides
    @Singleton
    fun provideUseCase(repository: StandingRepository): GetStandingsUseCase {
        return GetStandingsUseCase(repository)
    }
}