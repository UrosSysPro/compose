package net.systemvi.configurator.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import net.systemvi.configurator.components.neo_configure.key_selector.NeoConfigKeySelector
import net.systemvi.configurator.components.neo_configure.NeoConfigKeymap
import net.systemvi.configurator.components.neo_configure.NeoConfigureViewModel
import net.systemvi.configurator.components.neo_configure.keymap_selector.NeoConfigKeymapSelector
import net.systemvi.configurator.components.neo_configure.port_selector.NeoConfigPortSelector
import net.systemvi.configurator.utils.services.KeymapService
import net.systemvi.configurator.utils.services.SerialApiService

@Composable
fun NeoConfigPage() {
    val serialApi = viewModel { SerialApiService() }.serialApi
    val keymapApi = viewModel { KeymapService() }.keymapApi
    val neoConfigViewModel= viewModel { NeoConfigureViewModel() }

    DisposableEffect(Unit) {
        neoConfigViewModel.onStart(keymapApi,serialApi)
        onDispose {
            neoConfigViewModel.onStop()
        }
    }

    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row (
            modifier = Modifier
                .padding(
                    horizontal = 30.dp,
                    vertical = 15.dp
                )
                .fillMaxWidth()
                .zIndex(2f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top,
        ){
            NeoConfigKeySelector()
            NeoConfigKeymapSelector()
            NeoConfigPortSelector()
        }
        Column (
            modifier = Modifier
                .fillMaxSize()
                .zIndex(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NeoConfigKeymap()
        }
        Box(modifier = Modifier.height(100.dp)){}
    }
}