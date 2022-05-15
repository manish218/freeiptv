package com.spark.channelshome.data

import com.spark.channelshome.domain.ChannelsRepository
import com.spark.channelshome.domain.model.Channel

class ChannelsRepositoryImpl: ChannelsRepository {
    override suspend fun getChannels(): Result<List<Channel>> {
        TODO("Not yet implemented")
    }
}