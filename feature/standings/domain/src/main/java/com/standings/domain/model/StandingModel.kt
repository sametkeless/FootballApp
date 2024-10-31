package com.standings.domain.model

import com.core.base.BaseAppModel

data class StandingModel(
    val leagueName: String,
    val country: String,
    val teamName: String,
    val season: Int,
    val rank: Int,
    val points: Int,
    val goalsDiff: Int,
    val otherInfo: String,
    val logo: String
) : BaseAppModel()