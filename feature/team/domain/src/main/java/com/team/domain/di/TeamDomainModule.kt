package com.team.domain.di

import com.team.domain.repository.TeamRepository
import com.team.domain.usecase.GetTeamsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TeamDomainModule {

    @Provides
    @Singleton
    fun provideUseCase(repository: TeamRepository): GetTeamsUseCase {
        return GetTeamsUseCase(repository)
    }

}