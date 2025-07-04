package net.systemvi.configurator.components.configure.keyboard_layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import net.systemvi.configurator.components.configure.ConfigureViewModel
import net.systemvi.configurator.model.KeyMap
import net.systemvi.configurator.model.name

@Composable fun SaveAsDialog(onDismiss: () -> Unit) {
    val viewModel=viewModel { ConfigureViewModel() }
    viewModel.keymapApi.keymap.onSome { keymap ->
        var name by remember { mutableStateOf("") }
        Dialog(onDismissRequest = onDismiss) {
            Card(
                modifier = Modifier.width(400.dp).height(200.dp),
                shape = RoundedCornerShape(16.dp),

                ) {
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxSize().padding(30.dp)
                ) {
                    Box(
                        Modifier.weight(1f,)
                    ){
                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            label = { Text("New keymap name") },
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ){
                        OutlinedButton(onClick = onDismiss) {
                            Text("Cancel")
                        }
                        Box(Modifier.width(20.dp)){}
                        OutlinedButton(onClick = {
                            viewModel.keymapSaveAs(KeyMap.name.set(keymap,name))
                            onDismiss()
                        }) {
                            Text("Save Copy")
                        }
                    }
                }
            }
        }
    }
}