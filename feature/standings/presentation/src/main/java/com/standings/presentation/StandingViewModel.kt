package com.standings.presentation

import android.content.Context
import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.core.base.Resource
import com.standings.domain.model.GetStandingRequestModel
import com.standings.domain.model.StandingModel
import com.standings.domain.usecase.GetStandingsUseCase
import com.standings.presentation.navigation.StandingRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class StandingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getStandingsUseCase: GetStandingsUseCase
) :
    ViewModel() {

    private val _standingData = MutableStateFlow<StandingUiState>(StandingUiState.Loading)
    val standingData: StateFlow<StandingUiState> = _standingData

    init {
        val route = savedStateHandle.toRoute<StandingRoute>()

        getStandings(route.teamID ?: -1)
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
                                    StandingUiState.Error(errResID)
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
    data class Error(@StringRes val error: Int) : StandingUiState
    data class Success(val uiModel: StandingModel) : StandingUiState
}