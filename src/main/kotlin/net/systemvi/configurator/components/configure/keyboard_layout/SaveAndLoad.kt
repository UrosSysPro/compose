package net.systemvi.configurator.components.configure.keyboard_layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import net.systemvi.configurator.components.configure.ConfigureViewModel
import net.systemvi.configurator.data.defaultKeymaps
import net.systemvi.configurator.model.KeyMap

@Composable fun KeymapsRow(title:String, keymaps: List<KeyMap>) {
    Text(title)
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
    ){
        keymaps.forEach { keymap->
            KeymapPreview(keymap)
        }
    }
}

@Composable fun SaveAndLoad(){
    val viewModel= viewModel { ConfigureViewModel() }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ){
        KeymapsRow("Default",defaultKeymaps())
        KeymapsRow("Saved",viewModel.savedKeymaps)
    }
}