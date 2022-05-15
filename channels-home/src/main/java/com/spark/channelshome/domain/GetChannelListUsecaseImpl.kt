package com.spark.channelshome.domain

import com.spark.channelshome.domain.model.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

private class GetChannelListUsecaseImpl() : GetChannelsListUsecase {
    override val channelsList = MutableStateFlow<List<Channel>>(emptyList())

    init {
        // fetch channels data from the repository
    }

    override fun refresh() {

    }
}