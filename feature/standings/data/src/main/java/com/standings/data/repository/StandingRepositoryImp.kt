package com.standings.data.repository

import com.core.base.Resource
import com.core.network.helper.safeApiCall
import com.standings.data.service.StandingService
import com.standings.domain.model.GetStandingRequestModel
import com.standings.domain.model.StandingModel
import com.standings.domain.repository.StandingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StandingRepositoryImp @Inject constructor(private val api: StandingService) :
    StandingRepository {
    override suspend fun getStandings(requestModel: GetStandingRequestModel): Flow<Resource<StandingModel>> {
        return safeApiCall { api.getStandings(requestModel.teamID, requestModel.season) }
    }
}