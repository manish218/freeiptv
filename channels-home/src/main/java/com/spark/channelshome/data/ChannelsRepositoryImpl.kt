package com.spark.channelshome.data

import com.spark.channelshome.domain.ChannelsRepository
import com.spark.channelshome.domain.model.Channel
import kotlinx.coroutines.flow.StateFlow

class ChannelsRepositoryImpl(private val dataSource: ChannelsDataSource) :
    ChannelsRepository {
    override val channelsFlow: StateFlow<Result<List<Channel>>> = dataSource.channelsFlow

    override suspend fun refreshChannels(forceRefresh: Boolean) {
        dataSource.refreshChannels(forceRefresh)
    }
}