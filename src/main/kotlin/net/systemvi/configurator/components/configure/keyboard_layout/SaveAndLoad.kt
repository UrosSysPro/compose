package net.systemvi.configurator.components.configure.keyboard_layout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import net.systemvi.configurator.components.configure.ConfigureViewModel
import net.systemvi.configurator.data.defaultKeymaps
import net.systemvi.configurator.model.KeyMap

@Composable fun KeymapsRow(title:String, keymaps: List<KeyMap>) {
    val viewModel= viewModel { ConfigureViewModel() }

    Column {
        Text(title, style = MaterialTheme.typography.titleMedium)
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top=20.dp, bottom = 20.dp)
        ){
            keymaps.forEach { keymap->
                KeymapPreview(keymap,{viewModel.keymapLoad(keymap)})
            }
        }
    }
}

@Composable fun SaveAndLoad(){
    val viewModel= viewModel { ConfigureViewModel() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ){
        KeymapsRow("Default",defaultKeymaps())
        KeymapsRow("Saved",viewModel.keymapApi.savedKeymaps)
    }
}