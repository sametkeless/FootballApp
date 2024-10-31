package com.standings.domain.usecase

import com.core.base.Resource
import com.standings.domain.model.GetStandingRequestModel
import com.standings.domain.model.StandingModel
import com.standings.domain.repository.StandingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStandingsUseCase @Inject constructor(private val repository: StandingRepository) {
    suspend operator fun invoke(requestModel: GetStandingRequestModel): Flow<Resource<StandingModel>> {
        return repository.getStandings(requestModel)
    }
}