package com.spark.channelshome.data.mapper

import com.spark.channelshome.data.model.IPTVChannel
import com.spark.channelshome.domain.model.Channel

class IPTVChannelToChannelMapper {
    fun map(iptvChannel: List<IPTVChannel>): List<Channel> {
        return iptvChannel.filter { iptvChannel -> (!iptvChannel.id.isNullOrEmpty() && !iptvChannel.name.isNullOrEmpty()) }
            .map {
                with(it) {
                    Channel(
                        channelId = id.orEmpty(),
                        title = name.orEmpty(),
                        broadcaster = network,
                        broadcastCountry = subdivision,
                        broadcastRegion = broadcast_area,
                        broadcastCity = city,
                        languages = languages,
                        categories = categories,
                        isAdultChannel = is_nsfw,
                        websiteUrl = website,
                        logoUrl = logo
                    )
                }
            }
    }
}