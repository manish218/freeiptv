package com.spark.channelshome.domain

import com.spark.channelshome.domain.model.Channel
import javax.inject.Inject

interface ChannelDataValidator {
    fun isValid(channel: Channel): Boolean
}

class ChannelDataValidatorImpl @Inject constructor() : ChannelDataValidator {
    override fun isValid(channel: Channel): Boolean {
        return !channel.isAdultChannel && channel.categories.isNotEmpty()
    }
}