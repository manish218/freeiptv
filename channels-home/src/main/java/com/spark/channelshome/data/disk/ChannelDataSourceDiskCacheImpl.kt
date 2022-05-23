package com.spark.channelshome.data.disk

import android.content.SharedPreferences
import com.spark.channelshome.data.ChannelsDataSource
import com.spark.channelshome.data.ChannelsDataSourceCached
import com.spark.channelshome.data.ChannelsDataSourceCached.Companion.DEFAULT_EXPIRY_TIME_SEC
import com.spark.channelshome.data.roomdb.ChannelDao
import com.spark.channelshome.data.roomdb.ChannelEntity
import com.spark.channelshome.domain.model.Channel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ChannelDataSourceDiskCacheImpl @Inject constructor(
    override val backingCache: ChannelsDataSource,
    private val channelDao: ChannelDao,
    private val sharedPreferences: SharedPreferences,
) : ChannelsDataSourceCached {

    override val expiryInSec: Int = DEFAULT_EXPIRY_TIME_SEC
    override val coroutineScope = CoroutineScope(Job() + Dispatchers.IO)

    init {
        coroutineScope.launch {
            refreshChannels()
            backingCache.channelsFlow.collect { result ->
                result.fold(
                    onSuccess = { networkChannels ->
                        channelDao.deleteAll()
                        channelDao.insertAll(channels.map { ChannelEntity.fromChannel(it) })
                        sharedPreferences.edit()
                            .putLong(LAST_DISK_CACHE_UPDATE_TIME_KEY, currentTime)
                            .commit()
                        setChannelsToInMemCache(networkChannels)
                        channelsFlow.value = Result.success(networkChannels)
                    },
                    onFailure = {
                        channelsFlow.value = Result.failure(it)
                    }
                )
            }
        }
    }

    override var channelsFlow = MutableStateFlow<Result<List<Channel>>>(Result.success(emptyList()))

    override suspend fun refreshChannels(forceRefresh: Boolean) {
        if (forceRefresh || hasExpired()) {
            backingCache.refreshChannels(forceRefresh)
        } else if (channels.isEmpty()) {
            setChannelsToInMemCache(channelDao.getAll().map { it.toChannel() })
            channelsFlow.value = Result.success(channels)
        } else {
            channelsFlow.value = Result.success(channels)
        }
    }

    override fun cleanUp() {
        coroutineScope.cancel()
    }

    private fun setChannelsToInMemCache(channelsList: List<Channel>) {
        channels.clear()
        channels.addAll(channelsList)
    }

    private fun timeProvider() = Calendar.getInstance().time

    private fun hasExpired() = timeProvider().time >= cacheExpiryTimeSec
    private val channels = mutableListOf<Channel>()
    private val currentTime: Long
        get() = timeProvider().time
    private var cacheExpiryTimeSec =
        expiryInSec + sharedPreferences.getLong(LAST_DISK_CACHE_UPDATE_TIME_KEY, 0)

    private companion object {
        const val LAST_DISK_CACHE_UPDATE_TIME_KEY = "LAST_DISK_CACHE_UPDATE_TIME_KEY"
    }
}