package com.team.domain.repository

import com.core.base.Resource
import com.team.domain.model.TeamInfos
import kotlinx.coroutines.flow.Flow

interface TeamRepository {
    suspend fun getTeams(query: String): Flow<Resource<TeamInfos>>
}