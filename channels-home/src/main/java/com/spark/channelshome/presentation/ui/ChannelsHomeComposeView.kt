package com.spark.channelshome.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spark.channelshome.domain.model.Channel
import com.spark.channelshome.presentation.viewmodel.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Preview(showSystemUi = true)
@Composable
fun channelsHome(uiState: StateFlow<UiState> = MutableStateFlow(UiState.Loading)) {
    val uiStateCollector = uiState.collectAsState()
    when (val uiStateValue = uiStateCollector.value) {
        is UiState.Loading -> getLoadingUi()
        is UiState.LoadingError -> getErrorUi()
        is UiState.ChannelsList -> {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(uiStateValue.channels) {
                    getChannelItem(channel = it)
                }
            }
        }
    }
}

@Composable
private fun getLoadingUi() {
    CircularProgressIndicator()
}

@Composable
private fun getErrorUi() {
    Text(text = "Error fetching channels....")
}

@Composable
private fun getChannelItem(channel: Channel) {
    Text(text = channel.toString())
}