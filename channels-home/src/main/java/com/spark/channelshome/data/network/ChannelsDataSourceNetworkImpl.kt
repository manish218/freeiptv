package com.spark.channelshome.data.network

import com.spark.channelshome.data.ChannelsDataSource
import com.spark.channelshome.data.mapper.IPTVChannelToChannelMapper
import com.spark.channelshome.data.retrofit.ChannelsRetrofitService
import com.spark.channelshome.domain.model.Channel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow

class ChannelsDataSourceNetworkImpl(
    private val channelApi: ChannelsRetrofitService,
) : ChannelsDataSource {

    override lateinit var coroutineScope: CoroutineScope
    override val channelsFlow: MutableStateFlow<Result<List<Channel>>> = MutableStateFlow(
        Result.success(
            emptyList()
        )
    )

    override suspend fun refreshChannels(forceRefresh: Boolean) {
        return coroutineScope.runCatching { channelApi.getAllChannels() }.fold(
            onSuccess = { channels ->
                channelsFlow.value =
                    Result.success(IPTVChannelToChannelMapper().map(channels.body().orEmpty()))
            },
            onFailure = { exception -> channelsFlow.value = Result.failure(exception) }
        )
    }
}