package com.spark.channelshome.data

import java.util.*

interface ChannelsDataSourceCached : ChannelsDataSource {
    val backingCache: ChannelsDataSource
    val expiryInSec: Int
    val timeProvider: () -> Date

    companion object {
        const val DEFAULT_EXPIRY_TIME_SEC = 60 * 10 // 10 mins
    }
}