package net.systemvi.configurator.components.design

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import net.systemvi.configurator.model.KeyMap
import net.systemvi.configurator.utils.KeymapService

@Composable
fun SaveAsButton(keymap: KeyMap, onNameChange: (String) -> Unit) {
    val keymapService = viewModel { KeymapService() }
    val viewModel = viewModel { DesignPageViewModel() }
    val textColor = MaterialTheme.colorScheme.primaryContainer
    val contentColor = MaterialTheme.colorScheme.primary
    val color = MaterialTheme.colorScheme.onPrimaryContainer

    ElevatedButton(
        onClick = {
            viewModel.showSaveButton = true
            keymapService.keymapApi.saveAs(keymap)
        }
    ) {
        Text(text = "Save as")
    }

    if (viewModel.showSaveButton) {
        Dialog(
            onDismissRequest = { viewModel.showSaveButton = false },
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .size(400.dp)
                    .background(
                        color = contentColor,
                    )
                    .border(width = 1.dp, color = textColor, shape = RoundedCornerShape(10.dp))
            ) {
                OutlinedTextField(
                    value = keymap.name,
                    onValueChange = onNameChange,
                    label = { Text(text = "New Keymap", color = color) },
                    colors =  TextFieldDefaults.colors(focusedTextColor = contentColor),
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp)
                ) {
                    ElevatedButton(
                        onClick = { viewModel.showSaveButton = false }
                    ) {
                        Text("Cancel", color = contentColor)
                    }
                    ElevatedButton(
                        onClick = {
                            keymapService.keymapApi.saveAs(keymap)
                            viewModel.showSaveButton = false
                        }
                    ) {
                        Text("Save", color = contentColor)
                    }
                }
            }
        }
    }
}