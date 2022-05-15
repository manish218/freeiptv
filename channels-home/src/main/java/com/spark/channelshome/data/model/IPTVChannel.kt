package com.spark.channelshome.data.model

class IPTVChannel(
    val id: String?,
    val name: String?,
    val native_name: String?,
    val network: String?,
    val country: String?,
    val subdivision: String?,
    val city: String?,
    val broadcast_area: String?,
    val languages: List<String>,
    val categories: List<String>,
    val is_nsfw: Boolean,
    val launched: String?,
    val closed: String?,
    val replaced_by: String?,
    val website: String?,
    val logo: String?
)