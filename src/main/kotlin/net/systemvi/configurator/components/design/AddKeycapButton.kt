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
import arrow.core.right
import arrow.optics.dsl.index
import net.systemvi.configurator.data.allKeys
import net.systemvi.configurator.model.KeyMap
import net.systemvi.configurator.model.Keycap
import net.systemvi.configurator.model.keycaps

@Composable
fun AddKeycapButton(keymap: KeyMap, row: Int, onClick: (keymap: KeyMap) -> Unit, disable: Boolean) {
    FloatingActionButton(
        onClick = {
            if(!disable){
                onClick(KeyMap.keycaps.index(row).modify(keymap, {
                    it + Keycap(
                        listOf(allKeys[0].right())
                    )
                }))
            }
        },
        containerColor = if(!disable) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceDim,
        contentColor = if(!disable) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface,
        modifier = Modifier
            .size(50.dp)
//            .padding(end = 10.dp)
    ) {
//        Text(
//            "Add Keycap",
//            style = MaterialTheme.typography.bodySmall
//        )
        Icon(Icons.Filled.Add, contentDescription = "Add Keycap")
    }
}