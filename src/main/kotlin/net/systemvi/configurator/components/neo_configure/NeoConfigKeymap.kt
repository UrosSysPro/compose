package net.systemvi.configurator.components.neo_configure

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import net.systemvi.configurator.components.neo_configure.keymap.KeymapGrid
import net.systemvi.configurator.components.neo_configure.keymap.PleaseSelectKeymapOrPort

@Composable
fun NeoConfigKeymap(){
    val neoConfigViewModel = viewModel { NeoConfigureViewModel() }
    val keymap = neoConfigViewModel.keymap

    keymap.onSome { keymap ->
        KeymapGrid()
    }

    keymap.onNone {
        PleaseSelectKeymapOrPort()
    }
}