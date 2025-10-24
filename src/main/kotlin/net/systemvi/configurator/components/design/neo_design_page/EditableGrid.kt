package net.systemvi.configurator.components.design.neo_design_page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import net.systemvi.configurator.components.common.DraggableList
import net.systemvi.configurator.components.common.keyboard_grid.KeycapComponent
import net.systemvi.configurator.data.KeycapSizes
import net.systemvi.configurator.model.KeyMap
import net.systemvi.configurator.model.Keycap
import net.systemvi.configurator.model.addRow

@Composable
fun EditableGrid(keymap: KeyMap, onChange: (KeyMap) -> Unit, keycapType: KeycapComponent, onSelection: (List<Keycap>) -> Unit) {
    val oneUSize = KeycapSizes.Standard.size
    var selectedKeycaps by remember { mutableStateOf(emptyList<Keycap>()) }

    LaunchedEffect(selectedKeycaps) {
        onSelection(selectedKeycaps)
    }

    fun toggle(keycap: Keycap) {
        selectedKeycaps = if (selectedKeycaps.contains(keycap)) {
            selectedKeycaps - keycap
        } else {
            selectedKeycaps + keycap
        }
    }

    Column(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.primary
            )
    ) {
        AddRowButton(
            onClick = { onChange(keymap.addRow()) },
            disable = keymap.keycaps.size >= 10,
            KeycapSizes.Standard.size
        )
        DraggableList(
            items = keymap.keycaps,
            key = { it },
            onDrop = {}
        ) { i, row, isSelected ->
            KeymapRow(
                keymap, row, i, isSelected, keycapType, { keycap -> toggle(keycap) }, onChange
            )
        }
    }
}