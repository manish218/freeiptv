package com.spark.channelshome.data.di

import android.content.Context
import android.content.SharedPreferences
import com.spark.channelshome.data.ChannelsDataSource
import com.spark.channelshome.data.ChannelsDataSourceCached
import com.spark.channelshome.data.disk.ChannelDataSourceDiskCacheImpl
import com.spark.channelshome.data.mapper.IPTVChannelToChannelMapper
import com.spark.channelshome.data.network.ChannelsDataSourceNetworkImpl
import com.spark.channelshome.data.retrofit.ChannelsRetrofitService
import com.spark.channelshome.data.roomdb.ChannelsDb
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
abstract class DataBinderModules {
    @Binds
    abstract fun bindChannelsCacheDataSource(diskCacheDataSourceDiskCacheImpl: ChannelDataSourceDiskCacheImpl): ChannelsDataSourceCached

    @Binds
    abstract fun bindChannelsNetworkDataSource(networkImpl: ChannelsDataSourceNetworkImpl): ChannelsDataSource

}

@Module
@InstallIn(SingletonComponent::class)
object DataProviderModules {
    @Provides
    @Singleton
    fun retrofitService(
        @ApplicationContext appContext: Context
    ): ChannelsRetrofitService {
        return ChannelsRetrofitService.getInstance(appContext)
    }

    @Provides
    @Singleton
    fun channelsDb(@ApplicationContext appContext: Context): ChannelsDb {
        return ChannelsDb.getChannelsDb(appContext)
    }

    @Provides
    @Singleton
    fun channelDao(channelsDb: ChannelsDb) = channelsDb.channelDao()

    @Provides
    fun iPTVChannelToChannelMapper() = IPTVChannelToChannelMapper()

    @Provides
    fun sharedPreferences(@ApplicationContext appContext: Context): SharedPreferences = appContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

    private const val SHARED_PREF_NAME = "DATA_SHARED_PREF_KEY"
}