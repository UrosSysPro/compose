package net.systemvi.configurator.components.configure

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import net.systemvi.configurator.components.common.BorderHorizontal
import net.systemvi.configurator.components.configure.keyboard_keys.KeyboardKeys
import net.systemvi.configurator.components.configure.keyboard_layout.KeyboardLayoutView
import net.systemvi.configurator.utils.KeymapService
import net.systemvi.configurator.utils.SerialApiService

@Composable fun ConfigurePage(
    modifier: Modifier,
){
    val serialApiService=viewModel { SerialApiService() }
    val keymapApiService=viewModel { KeymapService() }
    val configureViewModel=viewModel { ConfigureViewModel() }

    DisposableEffect(Unit){
        configureViewModel.onStart(
            keymapApi = keymapApiService.keymapApi,
            serialApi = serialApiService.serialApi
        )
        onDispose {
            configureViewModel.onStop()
        }
    }

    Column(modifier) {
        Box(Modifier.weight(1f)){KeyboardLayoutView()}
        BorderHorizontal()
        Box(Modifier.weight(1f)){KeyboardKeys()}
    }
}