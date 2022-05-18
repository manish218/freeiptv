package com.spark.channelshome.presentation.viewmodel

import com.spark.channelshome.domain.model.Channel


sealed class UiState {
    object Loading : UiState()
    object LoadingError: UiState()
    class ChannelsList(val channels: List<Channel>) : UiState()
}