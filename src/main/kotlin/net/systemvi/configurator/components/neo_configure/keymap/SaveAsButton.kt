package net.systemvi.configurator.components.neo_configure.keymap

import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import net.systemvi.configurator.components.common.dialog.SaveAsDialog
import net.systemvi.configurator.components.neo_configure.NeoConfigureViewModel

@Composable
fun SaveAsButton() {
    var show by remember { mutableStateOf(false) }

    val neoConfigViewModel = viewModel { NeoConfigureViewModel() }
    ElevatedButton(
        onClick = {show=true}
    ){
        Text("Save As")
    }
    SaveAsDialog(
        show = show,
        label = "New Name",
        onSave = {name->neoConfigViewModel.createCopyOfKeymap(name);show=false},
        onCancel = {show=false}
    )
}