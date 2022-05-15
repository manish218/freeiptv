package com.spark.channelshome.data.cache

import android.content.SharedPreferences
import com.spark.channelshome.data.cache.ChannelsDataSourceCached.Companion.DEFAULT_EXPIRY_TIME_SEC
import com.spark.channelshome.data.roomdb.ChannelDao
import com.spark.channelshome.data.roomdb.ChannelEntity
import com.spark.channelshome.domain.model.Channel
import java.util.*

class ChannelDataSourceDiskCacheImpl(
    override val expiryInSec: Int = DEFAULT_EXPIRY_TIME_SEC,
    override val timeProvider: () -> Date,
    override val backingCache: ChannelsDataSource,
    private val channelDao: ChannelDao,
    private val sharedPreferences: SharedPreferences
) : ChannelsDataSourceCached {

    override suspend fun getChannels(forceRefresh: Boolean): Result<List<Channel>> {
        return if (hasExpired()) {
            backingCache.getChannels(forceRefresh).onSuccess { networkChannels ->
                cacheExpiryTimeSec = timeProvider().time + expiryInSec
                channelDao.deleteAll()
                channelDao.insertAll(networkChannels.map { ChannelEntity.fromChannel(it) })
                sharedPreferences.edit().putLong(DISK_CACHE_EXPIRY_KEY, currentTime + expiryInSec)
                    .commit()
            }
        } else {
            Result.success(channelDao.getAll().map { it.toChannel() })
        }
    }

    private fun hasExpired() = timeProvider().time >= cacheExpiryTimeSec
    private val currentTime: Long
        get() = timeProvider().time
    private var cacheExpiryTimeSec =
        sharedPreferences.getLong(DISK_CACHE_EXPIRY_KEY, currentTime)

    private companion object {
        const val DISK_CACHE_EXPIRY_KEY = "DISK_CACHE_EXPIRY_KEY"
    }
}