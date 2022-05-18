package com.spark.channelshome.domain.model

data class Channel(
    val channelId: String,
    val title: String,
    val broadcaster: String? = null,
    val broadcastCountry: String? = null,
    val broadcastRegion: String? = null,
    val broadcastCity: String? = null,
    val languages: List<String>,
    val categories: List<String>,
    val isAdultChannel: Boolean,
    val websiteUrl: String? = null,
    val logoUrl: String? = null
)