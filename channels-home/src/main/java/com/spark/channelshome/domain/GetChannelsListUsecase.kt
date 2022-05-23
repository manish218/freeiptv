package com.spark.channelshome.domain

import com.spark.channelshome.domain.model.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

interface GetChannelsListUsecase {
    val sanitisedChannelsList: Flow<Result<List<Channel>>>

    // Force refresh channels listing
    suspend fun refreshChannels(forceRefresh: Boolean)
}

class GetChannelsListUsecaseImpl @Inject constructor(
    private val channelsRepository: ChannelsRepository,
    private val channelDataValidator: ChannelDataValidator
) : GetChannelsListUsecase {

    override val sanitisedChannelsList: Flow<Result<List<Channel>>> =
        channelsRepository.channelsFlow.transform { result ->
                emit(result.fold(
                    onSuccess = {
                        Result.success(it.filter { channel ->
                            channelDataValidator.isValid(
                                channel
                            )
                        })
                    },
                    onFailure = {
                        Result.failure(it)
                    }
                ))
        }

    override suspend fun refreshChannels(forceRefresh: Boolean) {
        channelsRepository.refreshChannels(forceRefresh)
    }
}