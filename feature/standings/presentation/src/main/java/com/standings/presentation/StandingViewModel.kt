package com.standings.presentation

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.base.Resource
import com.standings.domain.model.GetStandingRequestModel
import com.standings.domain.model.StandingModel
import com.standings.domain.usecase.GetStandingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StandingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getStandingsUseCase: GetStandingsUseCase,
    @ApplicationContext private val applicationContext: Context
) :
    ViewModel() {

    private val _standingData = MutableStateFlow<StandingUiState>(StandingUiState.Loading)
    val standingData: StateFlow<StandingUiState> = _standingData

    init {
        val teamId = savedStateHandle.get<Int>("teamId")
        if (teamId != null) {
            getStandings(teamId)
        } else {
            _standingData.value = StandingUiState.Error("Invalid team ID")
        }
    }

    private fun getStandings(teamID: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _standingData.emit(StandingUiState.Loading)
            getStandingsUseCase.invoke(GetStandingRequestModel(teamID, 2022))
                .collect { resourceFlow ->
                    when (resourceFlow) {
                        is Resource.Success -> {
                            _standingData.emit(StandingUiState.Success(resourceFlow.data))
                        }

                        is Resource.Error -> {
                            resourceFlow.error.resID?.let { errResID ->
                                _standingData.emit(
                                    StandingUiState.Error(
                                        applicationContext.getString(
                                            errResID
                                        )
                                    )
                                )
                            }
                        }
                    }
                }
        }
    }
}

sealed interface StandingUiState {
    data object Loading : StandingUiState
    data class Error(val error: String) : StandingUiState
    data class Success(val uiModel: StandingModel) : StandingUiState
}