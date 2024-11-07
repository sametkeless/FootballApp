package com.tolerans.footballapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.standings.presentation.navigation.navigateToStanding
import com.standings.presentation.navigation.standingScreen
import com.team.presentation.navigation.TeamRoute
import com.team.presentation.navigation.teamScreen
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

    NavHost(navController = navController, startDestination = TeamRoute) {
        teamScreen(
            onItemSelected = navController::navigateToStanding
        )
        standingScreen()
    }
}
