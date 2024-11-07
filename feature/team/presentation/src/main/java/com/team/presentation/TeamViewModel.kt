package com.team.presentation

import android.app.Application
import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.mutableStateOf
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
internal class TeamViewModel @Inject constructor(
    private val useCase: GetTeamsUseCase
) : ViewModel() {

    val query = mutableStateOf("")

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
                            resourceFlow.error.resID?.let { errResID ->
                                _teamData.emit(
                                    TeamUiState.Error(errResID)
                                )
                            }
                        }
                    }
                }
            } else {
                _teamData.emit(TeamUiState.Empty)
            }
        }
    }
}

internal sealed interface TeamUiState {
    data object Empty : TeamUiState
    data object Loading : TeamUiState
    data class Error(@StringRes val error: Int) : TeamUiState
    data class Success(val uiModel: TeamInfos) : TeamUiState
}