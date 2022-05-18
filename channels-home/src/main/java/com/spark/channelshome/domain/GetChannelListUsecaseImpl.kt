package com.spark.channelshome.domain

import com.spark.channelshome.domain.model.Channel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.transform

private class GetChannelListUsecaseImpl(
    private val channelsRepository: ChannelsRepository,
    private val channelDataValidator: ChannelDataValidator
) : GetChannelsListUsecase {

    override val sanitisedChannelsList: StateFlow<Result<List<Channel>>> =
        channelsRepository.channelsFlow.let { flow ->
            flow.transform<Result<List<Channel>>, Result<List<Channel>>> { result ->
                result.fold(
                    onSuccess = {
                        Result.success(it.filter { channel ->
                            channelDataValidator.isValid(
                                channel
                            )
                        })
                    },
                    onFailure = {
                        Result.failure<List<Channel>>(it)
                    }
                )
            } as StateFlow<Result<List<Channel>>>
        }

    override suspend fun refreshChannels(forceRefresh: Boolean) {
        channelsRepository.refreshChannels(forceRefresh)
    }
}