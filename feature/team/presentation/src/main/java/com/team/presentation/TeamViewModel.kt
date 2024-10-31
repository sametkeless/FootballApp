package com.team.presentation

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.base.Resource
import com.team.domain.model.TeamInfos
import com.team.domain.usecase.GetTeamsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamViewModel @Inject constructor(
    private val useCase: GetTeamsUseCase
) : ViewModel() {

    private val _teamData = MutableStateFlow<TeamUiState>(TeamUiState.Empty)
    val teamData: StateFlow<TeamUiState> = _teamData

    private var searchJob: Job? = null

    fun onSearchQueryChanged(query: String) {
        searchJob?.cancel()

        searchJob = viewModelScope.launch(Dispatchers.IO) {
            delay(1000)

            if (query.isNotEmpty() && query.length > 2) {
                _teamData.emit(TeamUiState.Loading)
                useCase.invoke(query).collect { resourceFlow ->
                    when (resourceFlow) {
                        is Resource.Success -> {
                            _teamData.emit(TeamUiState.Success(resourceFlow.data))
                        }
                        is Resource.Error -> {
                            _teamData.emit(TeamUiState.Error(resourceFlow.error.message ?: "Bilinmeyen Hata"))
                        }
                    }
                }
            } else {
                _teamData.emit(TeamUiState.Empty)
            }
        }
    }
}

sealed interface TeamUiState {
    data object Empty : TeamUiState
    data object Loading : TeamUiState
    data class Error(val error: String) : TeamUiState
    data class Success(val uiModel: TeamInfos) : TeamUiState
}