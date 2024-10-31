package com.team.data.model

import com.core.network.base.BaseNetworkDTOResponseModel
import com.team.domain.model.TeamModel
import com.team.domain.model.TeamInfoModel
import com.team.domain.model.TeamInfos
import com.team.domain.model.VenueModel

data class GetTeamResponseModel(
    val response: List<TeamInfoDTO>
) : BaseNetworkDTOResponseModel<TeamInfos>() {
    override suspend fun map() = TeamInfos(
        response.map { dtoModel ->
            TeamInfoModel(
                TeamModel(
                    dtoModel.team?.code.orEmpty(),
                    dtoModel.team?.country.orEmpty(),
                    dtoModel.team?.founded ?: -1,
                    dtoModel.team?.id ?: -1,
                    dtoModel.team?.logo.orEmpty(),
                    dtoModel.team?.name.orEmpty(),
                    dtoModel.team?.national ?: false
                ),
                VenueModel(
                    dtoModel.venue?.address.orEmpty(),
                    dtoModel.venue?.capacity ?: -1,
                    dtoModel.venue?.city.orEmpty(),
                    dtoModel.venue?.id ?: -1,
                    dtoModel.venue?.image.orEmpty(),
                    dtoModel.venue?.name.orEmpty(),
                    dtoModel.venue?.surface.orEmpty()
                )
            )
        }
    )
}