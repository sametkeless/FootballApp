package com.team.domain.usecase

import com.core.base.Resource
import com.team.domain.model.TeamInfos
import com.team.domain.repository.TeamRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTeamsUseCase @Inject constructor(private val repository: TeamRepository) {
    suspend operator fun invoke(query: String): Flow<Resource<TeamInfos>> {
        return repository.getTeams(query)
    }
}