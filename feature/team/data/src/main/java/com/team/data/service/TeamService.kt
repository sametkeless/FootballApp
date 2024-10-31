package com.team.data.service

import com.team.data.model.GetTeamResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TeamService {

    @GET("/teams")
    suspend fun getTeams(@Query("search") query: String): Response<GetTeamResponseModel>
}