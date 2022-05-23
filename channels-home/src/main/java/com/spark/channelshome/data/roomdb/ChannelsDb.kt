package com.spark.channelshome.data.roomdb

import android.content.Context
import androidx.room.*

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = [ChannelEntity::class], version = 1, exportSchema = false)
@TypeConverters(StringListConverter::class)
abstract class ChannelsDb : RoomDatabase() {

    abstract fun channelDao(): ChannelDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: ChannelsDb? = null

        fun getChannelsDb(context: Context): ChannelsDb {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    ChannelsDb::class.java,
                    "channelsDb"
                ).build()
            }.also {
                INSTANCE = it
            }

        }
    }
}