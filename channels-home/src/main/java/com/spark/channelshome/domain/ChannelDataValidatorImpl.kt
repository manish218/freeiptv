package com.spark.channelshome.domain

import com.spark.channelshome.domain.model.Channel

class ChannelDataValidatorImpl : ChannelDataValidator {
    override fun isValid(channel: Channel): Boolean {
        return !channel.isAdultChannel && channel.categories.isNotEmpty()
    }
}