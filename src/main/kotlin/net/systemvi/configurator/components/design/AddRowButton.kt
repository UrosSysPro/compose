package net.systemvi.configurator.components.design

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
fun AddRowButton(keymap: KeyMap, onClick: (keymap: KeyMap) -> Unit, disable: Boolean) {
    FloatingActionButton(
        onClick = {
            if(!disable) {
                onClick(KeyMap.keycaps.modify(keymap, {
                    it + listOf(listOf())
                }))
            }
        },
        containerColor = if(!disable) MaterialTheme.colorScheme.tertiaryContainer else MaterialTheme.colorScheme.surfaceDim,
        contentColor = if(!disable) MaterialTheme.colorScheme.onTertiaryContainer else MaterialTheme.colorScheme.onSurface,
        modifier = Modifier
            .padding(vertical = 10.dp)
            .size(50.dp)
    ) {
//        Text(
//            "Add Row",
//            style = MaterialTheme.typography.bodySmall
//        )
        Icon(Icons.Filled.Add, contentDescription = "Add Row")
    }
}