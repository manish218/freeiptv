package com.spark.channelshome.domain

import com.spark.channelshome.domain.model.Channel

interface ChannelsRepository {
    suspend fun getChannels(): Result<List<Channel>>
}