package com.spark.channelshome.domain

import com.spark.channelshome.domain.model.Channel

interface ChannelDataValidator {
    fun isValid(channel: Channel): Boolean
}