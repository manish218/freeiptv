package com.spark.channelshome.data.roomdb

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.spark.channelshome.domain.model.Channel

@Entity(tableName = "channelsTable")
class ChannelEntity(
    @PrimaryKey val channelId: String,
    val title: String,
    val broadcaster: String?,
    val broadcastCountry: String?,
    val broadcastRegion: String?,
    val broadcastCity: String?,
    val languages: List<String>,
    @Embedded val categories: List<String>,
    val isAdultChannel: Boolean,
    val websiteUrl: String?,
    val logoUrl: String?
) {
    fun toChannel() = Channel(
        channelId = channelId,
        title = title,
        broadcaster = broadcaster,
        broadcastCountry = broadcastCountry,
        broadcastRegion = broadcastRegion,
        broadcastCity = broadcastCity,
        languages = languages,
        categories = categories,
        isAdultChannel = isAdultChannel,
        websiteUrl = websiteUrl,
        logoUrl = logoUrl
    )

    companion object {

        fun fromChannel(channel: Channel) = ChannelEntity(
            channelId = channel.channelId,
            title = channel.title,
            broadcaster = channel.broadcaster,
            broadcastCountry = channel.broadcastCountry,
            broadcastRegion = channel.broadcastRegion,
            broadcastCity = channel.broadcastCity,
            languages = channel.languages,
            categories = channel.categories,
            isAdultChannel = channel.isAdultChannel,
            websiteUrl = channel.websiteUrl,
            logoUrl = channel.logoUrl
        )
    }
}
