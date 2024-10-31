package com.standings.domain.repository

import com.core.base.Resource
import com.standings.domain.model.GetStandingRequestModel
import com.standings.domain.model.StandingModel
import kotlinx.coroutines.flow.Flow

interface StandingRepository {
    suspend fun getStandings(requestModel: GetStandingRequestModel): Flow<Resource<StandingModel>>
}