package com.standings.data.service

import com.standings.data.model.GetStandingsResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface StandingService {

    @GET("/standings")
    suspend fun getStandings(
        @Query("team") team: Int,
        @Query("season") season: Int
    ): Response<GetStandingsResponseModel>
}