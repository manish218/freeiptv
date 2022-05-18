package com.spark.channelshome.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spark.channelshome.domain.GetChannelsListUsecase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ChannelsHomeViewModel(
    private val getChannelsListUsecase: GetChannelsListUsecase
) : ViewModel() {

    private val uiStateMutableFlow: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)
    val uiStateFlow: StateFlow<UiState> = uiStateMutableFlow

    init {
        viewModelScope.launch {
            getChannelsListUsecase.sanitisedChannelsList.collect { result ->
                result.fold(
                    onSuccess = { uiStateMutableFlow.value = UiState.ChannelsList(it) },
                    onFailure = { UiState.LoadingError }
                )
            }
        }
    }

}