package com.spark.channelshome.domain

import com.spark.channelshome.domain.model.Channel
import kotlinx.coroutines.flow.StateFlow

interface ChannelsRepository {
    val channelsFlow: StateFlow<Result<List<Channel>>>
    suspend fun refreshChannels(forceRefresh: Boolean)
}