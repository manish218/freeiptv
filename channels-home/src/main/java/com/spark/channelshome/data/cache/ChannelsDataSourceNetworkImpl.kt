package com.spark.channelshome.data.cache

import com.spark.channelshome.data.mapper.IPTVChannelToChannelMapper
import com.spark.channelshome.data.retrofit.ChannelsRetrofitService
import com.spark.channelshome.domain.model.Channel

class ChannelsDataSourceNetworkImpl(
    private val channelApi: ChannelsRetrofitService
) : ChannelsDataSource {

    override suspend fun getChannels(forceRefresh: Boolean): Result<List<Channel>> {
        return runCatching { channelApi.getAllChannels() }.fold(
            onSuccess = { channels ->
                Result.success(IPTVChannelToChannelMapper().map(channels.body().orEmpty()))
            },
            onFailure = { exception -> Result.failure(exception) }
        )
    }
}