package net.systemvi.configurator.page

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.onClick
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

@OptIn(ExperimentalFoundationApi::class, ExperimentalStdlibApi::class)
@Composable
fun NeoConfigPage() {
    val serialApi = viewModel { SerialApiService() }.serialApi
    val keymapApi = viewModel { KeymapService() }.keymapApi
    val neoConfigViewModel= viewModel { NeoConfigureViewModel() }

    var portPickerOpened by remember { mutableStateOf(false) }
    var keymapPickerOpened by remember { mutableStateOf(false) }
    var keyPickerOpened by remember { mutableStateOf(false) }

    var anyPickerOpened=portPickerOpened||keymapPickerOpened||keyPickerOpened

    val modalOpacity by animateFloatAsState(if(anyPickerOpened)0.5f else 0.0f)


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
                .zIndex(3f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top,
        ){
            NeoConfigKeySelector(
                keyPickerOpened,
                {
                    keyPickerOpened=it
                    if(it){
                        portPickerOpened = false
                        keymapPickerOpened = false
                    }
                }
            )
            NeoConfigKeymapSelector(
                keymapPickerOpened,
                {
                    keymapPickerOpened=it
                    if(it){
                        portPickerOpened = false
                        keyPickerOpened = false
                    }
                }
            )
            NeoConfigPortSelector(
                portPickerOpened,
                {
                    portPickerOpened=it
                    if(it){
                        keymapPickerOpened = false
                        keyPickerOpened = false
                    }
                }
            )
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
        Box(modifier = Modifier.zIndex(2f).height(100.dp)){
            Box(
                modifier = Modifier
                    .wrapContentWidth(unbounded = true)
                    .wrapContentHeight(unbounded = true)
                    .width(10000.dp)
                    .height(10000.dp)
                    .background(MaterialTheme.colorScheme.surfaceDim.copy(alpha = modalOpacity))
                    .onClick(enabled = anyPickerOpened){
                        portPickerOpened = false
                        keymapPickerOpened = false
                        keyPickerOpened = false
                    }
            ){}
        }
    }
}