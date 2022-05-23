package com.spark.channelshome.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.spark.channelshome.presentation.viewmodel.ChannelsHomeViewModel
import com.spark.channelshome.presentation.viewmodel.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChannelsHomeFragment : Fragment() {
    private val viewModel = viewModels<ChannelsHomeViewModel>()
    private val uiStateMutable = MutableStateFlow<UiState>(UiState.Loading)

    init {
        lifecycleScope.launchWhenCreated {
            viewModel.value.uiStateFlow.collect{ uiState ->
                uiStateMutable.value = uiState
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                channelsHome(uiStateMutable)
            }
        }
    }
}