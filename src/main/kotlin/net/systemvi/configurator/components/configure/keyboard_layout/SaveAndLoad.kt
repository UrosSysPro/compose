package net.systemvi.configurator.components.configure.keyboard_layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import net.systemvi.configurator.components.configure.ConfigureViewModel

@Composable fun SaveAndLoad(){
    val viewModel= viewModel { ConfigureViewModel() }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ){
        Text("Saved Keymaps")
        FlowRow(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ){
            viewModel.savedKeymaps.forEach { keymap->
                KeymapPreview(keymap)
            }
        }
    }
}