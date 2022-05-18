package com.spark.channelshome.domain

import com.spark.channelshome.domain.model.Channel
import kotlinx.coroutines.flow.StateFlow

interface GetChannelsListUsecase {
    val sanitisedChannelsList: StateFlow<Result<List<Channel>>>

    // Force refresh channels listing
    suspend fun refreshChannels(forceRefresh: Boolean)
}