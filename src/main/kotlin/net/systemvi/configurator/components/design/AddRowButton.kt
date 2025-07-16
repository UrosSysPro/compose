package net.systemvi.configurator.components.design

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.systemvi.configurator.model.KeyMap
import net.systemvi.configurator.model.keycaps
import net.systemvi.configurator.model.padding

@Composable
fun AddRowButton(keymap: KeyMap, onClick: (keymap: KeyMap) -> Unit) {
    FloatingActionButton(
        onClick = {
            onClick(KeyMap.keycaps.modify(keymap, {
                it + listOf(listOf())
            }))
        },
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        modifier = Modifier
            .padding(vertical = 10.dp)
    ) {
//        Text(
//            "Add Row",
//            style = MaterialTheme.typography.bodySmall
//        )
        Icon(Icons.Filled.Add, contentDescription = "Add Row")
    }
}