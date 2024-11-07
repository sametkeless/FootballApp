package com.team.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.team.presentation.TeamScreen
import kotlinx.serialization.Serializable

@Serializable
data object TeamRoute

fun NavController.navigateToTeam(navOptions: NavOptions) =
    navigate(route = TeamRoute, navOptions)

fun NavGraphBuilder.teamScreen(onItemSelected: (Int) -> Unit) {
    composable<TeamRoute>() {
        TeamScreen(onTeamSelected = onItemSelected)
    }
}