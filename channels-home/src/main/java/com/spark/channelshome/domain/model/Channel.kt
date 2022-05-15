package com.spark.channelshome.domain.model

data class Channel(
    val channelId: String,
    val title: String,
    val broadcaster: String?,
    val broadcastCountry: String?,
    val broadcastRegion: String?,
    val broadcastCity: String?,
    val languages: List<String>,
    val categories: List<String>,
    val isAdultChannel: Boolean,
    val websiteUrl: String?,
    val logoUrl: String?
)