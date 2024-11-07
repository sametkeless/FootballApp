package com.standings.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.core.ui.NetworkImageView
import com.standings.domain.model.StandingModel

@Composable
internal fun StandingScreen(viewModel: StandingViewModel = hiltViewModel()) {
    val standingData = viewModel.standingData.collectAsState()
    val standingStateUIModel = standingData.value

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when (standingStateUIModel) {
            is StandingUiState.Error -> {
                Text(
                    text = stringResource(standingStateUIModel.error),
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            is StandingUiState.Loading -> {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }

            is StandingUiState.Success -> {
                StandingCard(standingStateUIModel.uiModel)
            }
        }
    }
}

@Composable
internal fun StandingCard(standingModel: StandingModel) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "${standingModel.leagueName} (${standingModel.country})",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            NetworkImageView(
                url = standingModel.logo,
                contentDescription = "League Logo",
                modifier = Modifier
                    .size(100.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = standingModel.teamName,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextInfoRow(label = "Season", value = standingModel.season.toString())
            TextInfoRow(label = "Rank", value = standingModel.rank.toString())
            TextInfoRow(label = "Points", value = standingModel.points.toString())
            TextInfoRow(label = "Goal Difference", value = standingModel.goalsDiff.toString())

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = standingModel.otherInfo, fontSize = 14.sp)
        }
    }
}

@Composable
internal fun TextInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
    ) {
        Text(
            text = "$label: ",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        Text(text = value)
    }
}
