package net.systemvi.configurator.components.design

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
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

@Composable
fun RemoveRowButton(keymap: KeyMap, row: Int, onClick: (keymap: KeyMap) -> Unit) {
    FloatingActionButton(
        onClick = {
            onClick(KeyMap.keycaps.modify(keymap, {keycaps ->
                keycaps.filterIndexed {index, _ -> index != row}
            }))
        },
        containerColor = MaterialTheme.colorScheme.errorContainer,
        contentColor = MaterialTheme.colorScheme.onErrorContainer,
        modifier = Modifier
            .padding(horizontal = 10.dp)
    ) {
//        Text(
//            "Remove Row",
//            style = MaterialTheme.typography.bodySmall
//        )
        Icon(Icons.Filled.Delete, contentDescription = "Remove Row")
    }
}
