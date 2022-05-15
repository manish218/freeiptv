package com.spark.channelshome.domain

import com.spark.channelshome.domain.model.Channel
import kotlinx.coroutines.flow.StateFlow

interface GetChannelsListUsecase {
    val channelsList: StateFlow<List<Channel>>

    // Force refresh channels listing
    fun refresh()
}