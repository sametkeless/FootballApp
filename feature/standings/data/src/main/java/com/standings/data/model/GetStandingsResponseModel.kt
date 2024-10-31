package com.standings.data.model

import com.core.network.base.BaseNetworkDTOResponseModel
import com.standings.domain.model.StandingModel

data class GetStandingsResponseModel(
    val response: List<StandingInfoDTOModel>
) : BaseNetworkDTOResponseModel<StandingModel>() {
    override suspend fun map(): StandingModel {
        val league = response.firstOrNull()?.league
        val standing = league?.standings?.firstOrNull()?.firstOrNull()
        return StandingModel(
            league?.name.orEmpty(),
            league?.country.orEmpty(),
            standing?.team?.name.orEmpty(),
            league?.season ?: -1,
            standing?.rank ?: 0,
            standing?.points ?: 0,
            standing?.goalsDiff ?: 0,
            standing?.description.orEmpty(),
            standing?.team?.logo.orEmpty()
        )
    }
}