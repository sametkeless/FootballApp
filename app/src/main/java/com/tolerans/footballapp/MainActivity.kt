package com.tolerans.footballapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.standings.presentation.StandingScreen
import com.team.presentation.TeamScreen
import com.tolerans.footballapp.ui.theme.FootballAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FootballAppTheme {
                FootballNavHost()
            }
        }
    }
}

@Composable
fun FootballNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Team.route) {

        composable(route = Screen.Team.route) {
            TeamScreen(onTeamSelected = { teamID ->
                navController.navigate(Screen.Standing.createRoute(teamID))
            })
        }

        composable(
            route = "${Screen.Standing.route}/{teamId}",
            arguments = listOf(navArgument("teamId") {
                type = NavType.IntType
            })
        ) {
            StandingScreen()
        }
    }
}

sealed class Screen(val route: String) {

    data object Team : Screen("team")

    data object Standing : Screen("standing") {
        fun createRoute(teamId: Int): String = "$route/$teamId"
    }
}
