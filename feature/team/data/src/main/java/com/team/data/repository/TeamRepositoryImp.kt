package com.team.data.repository

import com.core.base.Resource
import com.core.network.helper.safeApiCall
import com.team.data.service.TeamService
import com.team.domain.model.TeamInfos
import com.team.domain.repository.TeamRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TeamRepositoryImp @Inject constructor(private val api: TeamService) : TeamRepository {
    override suspend fun getTeams(query: String): Flow<Resource<TeamInfos>> {
        return safeApiCall { api.getTeams(query) }
    }
}