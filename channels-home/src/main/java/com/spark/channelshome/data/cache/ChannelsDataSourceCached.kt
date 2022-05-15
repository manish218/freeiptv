package com.spark.channelshome.data.cache

import com.spark.channelshome.domain.model.Channel
import java.util.*

interface ChannelsDataSourceCached : ChannelsDataSource{
    val backingCache: ChannelsDataSource

    companion object {
        const val DEFAULT_EXPIRY_TIME_SEC = 60 * 10 // 10 mins
    }
}