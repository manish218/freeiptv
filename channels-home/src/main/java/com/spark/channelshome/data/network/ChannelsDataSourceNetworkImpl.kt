package com.spark.channelshome.data.network

import com.spark.channelshome.data.ChannelsDataSource
import com.spark.channelshome.data.mapper.IPTVChannelToChannelMapper
import com.spark.channelshome.data.retrofit.ChannelsRetrofitService
import com.spark.channelshome.domain.model.Channel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class ChannelsDataSourceNetworkImpl @Inject constructor(
    private val channelApi: ChannelsRetrofitService,
    private val iPTVChannelToChannelMapper: IPTVChannelToChannelMapper
) : ChannelsDataSource {

    override val coroutineScope = CoroutineScope(Job() + Dispatchers.IO)
    override val channelsFlow: MutableStateFlow<Result<List<Channel>>> = MutableStateFlow(
        Result.success(
            emptyList()
        )
    )

    override suspend fun refreshChannels(forceRefresh: Boolean) {
        return coroutineScope.runCatching { channelApi.getAllChannels() }.fold(
            onSuccess = { channels ->
                channelsFlow.value =
                    Result.success(iPTVChannelToChannelMapper.map(channels.body().orEmpty()))
            },
            onFailure = { exception -> channelsFlow.value = Result.failure(exception) }
        )
    }

    override fun cleanUp() {
        coroutineScope.cancel()
    }
}