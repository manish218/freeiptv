package com.spark.channelshome.presentation.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.spark.channelshome.domain.model.Channel

@Preview
@Composable
fun ChannelsHomeView(channels: List<Channel> = listOf(testChannelData(1), testChannelData(2))) {
    LazyColumn {
        items(channels) {
            getChannelItem(channel = it)
        }
    }
}

@Composable
private fun getChannelItem(channel: Channel) {
    Text(text = channel.toString())
}

private fun testChannelData(index: Int) =
    Channel(
        channelId = "id = $index", title = "title - $index", languages = emptyList(),
        categories = listOf("action"), isAdultChannel = false
    )