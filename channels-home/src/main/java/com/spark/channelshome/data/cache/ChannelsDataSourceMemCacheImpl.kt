package com.spark.channelshome.data.cache

import com.spark.channelshome.data.cache.ChannelsDataSourceCached.Companion.DEFAULT_EXPIRY_TIME_SEC
import com.spark.channelshome.domain.model.Channel
import java.util.*

class ChannelsDataSourceMemCacheImpl(
    override val backingCache: ChannelsDataSource,
    override val expiryInSec: Int = backingCache.expiryInSec,
    override val timeProvider: () -> Date = backingCache.timeProvider
) : ChannelsDataSourceCached {

    init {
        //Load data from disk cache and also trigger a network request
    }

    override suspend fun getChannels(forceRefresh: Boolean): Result<List<Channel>> {
        return if (forceRefresh || channels.isEmpty() || hasExpired()) {
            backingCache.getChannels(forceRefresh).onSuccess {
                cacheExpiryTimeSec = timeProvider().time + expiryInSec
                channels.clear()
                channels.addAll(it)
            }
        } else {
            Result.success(channels)
        }
    }

    private fun hasExpired() = timeProvider().time >= cacheExpiryTimeSec
    private val channels = mutableListOf<Channel>()
    private var cacheExpiryTimeSec = timeProvider().time
}
