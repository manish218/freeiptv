package com.spark.channelshome.data.cache

import com.spark.channelshome.domain.model.Channel
import java.util.*

interface ChannelsDataSource {
    val expiryInSec: Int
    val timeProvider: () -> Date
    suspend fun getChannels(forceRefresh: Boolean): Result<List<Channel>>
}