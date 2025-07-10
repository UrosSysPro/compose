package net.systemvi.configurator.components.design

import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import net.systemvi.configurator.model.KeyMap
import net.systemvi.configurator.model.keycaps
import kotlin.collections.plus

@Composable
fun AddRowButton(keymap: KeyMap, onClick: (keymap: KeyMap) -> Unit) {
    ElevatedButton(
        onClick = {
            onClick(KeyMap.keycaps.modify(keymap, {
                it + listOf(listOf())
            }))
        },
    ) {
        Text(
            "Add Row",
            style = MaterialTheme.typography.bodySmall
        )
    }
}