package com.spark.channelshome.data.roomdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ChannelDao {
    @Query("SELECT * FROM channelsTable")
    suspend fun getAll(): List<ChannelEntity>

    @Insert
    suspend fun insertAll(channels: List<ChannelEntity>)

    @Query("DELETE FROM channelsTable")
    suspend fun deleteAll()
}

