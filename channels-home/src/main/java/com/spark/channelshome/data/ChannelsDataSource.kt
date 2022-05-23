package com.spark.channelshome.data

import com.spark.channelshome.domain.model.Channel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow

interface ChannelsDataSource {
    val channelsFlow: MutableStateFlow<Result<List<Channel>>>
    val coroutineScope: CoroutineScope

    // fetch remote data and emit result on the flow above
    suspend fun refreshChannels(forceRefresh: Boolean = false)

    fun cleanUp()
}