package com.standings.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.standings.presentation.StandingScreen
import kotlinx.serialization.Serializable

@Serializable
data class StandingRoute(
    val teamID: Int
)

fun NavController.navigateToStanding(
    teamID: Int,
    navOptions: NavOptions? = null,
) {
    navigate(route = StandingRoute(teamID), navOptions)
}

fun NavGraphBuilder.standingScreen() {
    composable<StandingRoute> {
        StandingScreen()
    }
}