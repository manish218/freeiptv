package com.spark.channelshome.domain.di

import com.spark.channelshome.data.ChannelsRepositoryImpl
import com.spark.channelshome.domain.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DomainModules {
    @Binds
    abstract fun bindGetChannelsListUsecase(
        getChannelsListUsecase: GetChannelsListUsecaseImpl
    ): GetChannelsListUsecase

    @Binds
    abstract fun bindChannelValidator(
        channelValidator: ChannelDataValidatorImpl
    ): ChannelDataValidator

    @Binds
    abstract fun bindChannelsRepository(
        channelsRepository: ChannelsRepositoryImpl
    ): ChannelsRepository
}