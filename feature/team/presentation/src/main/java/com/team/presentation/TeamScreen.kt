package com.team.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.team.domain.model.TeamInfoModel

@Composable
fun TeamScreen(
    viewModel: TeamViewModel = hiltViewModel(),
    onTeamSelected: (Int) -> Unit
) {
    val teamData = viewModel.teamData.collectAsState()
    val teamStateUIModel = teamData.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        SearchScreen(onQueryChange = { query ->
            viewModel.onSearchQueryChanged(query) // Kullanıcı sorgusunu ViewModel'e gönder
        })

        when (teamStateUIModel) {
            is TeamUiState.Success -> {
                if (teamStateUIModel.uiModel.teamInfoList.isNotEmpty()) {
                    TeamList(teams = teamStateUIModel.uiModel.teamInfoList, onClickListener = onTeamSelected)
                }
            }

            is TeamUiState.Error -> {
                Text(
                    text = "Bir hata oluştu: ${teamStateUIModel.error}",
                    color = Color.Red,
                    modifier = Modifier.fillMaxSize(),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            is TeamUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            }

            else -> Unit
        }
    }
}

@Composable
fun SearchScreen(onQueryChange: (String) -> Unit) {
    var query by remember { mutableStateOf("") }

    OutlinedTextField(
        value = query,
        onValueChange = { newText ->
            query = newText
            onQueryChange(query) // Kullanıcı girdisini ViewModel'e gönder
        },
        label = { Text("Takım ya da Ülke Ara") },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun TeamList(teams: List<TeamInfoModel>, onClickListener: (Int) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp),
        contentPadding = PaddingValues(16.dp),
    ) {
        items(teams) { team ->
            TeamItem(team = team, onClickListener)
            HorizontalDivider(color = Color.Gray, thickness = 1.dp)
        }
    }
}

@Composable
fun TeamItem(team: TeamInfoModel, onClickListener: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.medium)
            .clickable { onClickListener(team.teamModel.id) }
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = team.teamModel.name,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = team.venueModel.city,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}
