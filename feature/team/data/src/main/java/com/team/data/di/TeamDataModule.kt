package com.team.data.di

import com.team.data.repository.TeamRepositoryImp
import com.team.data.service.TeamService
import com.team.domain.repository.TeamRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TeamDataModule {

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): TeamService {
        return retrofit.create(TeamService::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(service: TeamService): TeamRepository {
        return TeamRepositoryImp(service)
    }
}
