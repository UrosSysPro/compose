package net.systemvi.configurator.components.design.neo_design_page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import net.systemvi.configurator.components.common.DraggableList
import net.systemvi.configurator.components.common.DraggableListDirection
import net.systemvi.configurator.components.common.keyboard_grid.KeycapComponent
import net.systemvi.configurator.model.KeyMap
import net.systemvi.configurator.model.Keycap
import net.systemvi.configurator.model.addKeycap
import net.systemvi.configurator.model.deleteKeycap
import net.systemvi.configurator.model.removeRow

@Composable
fun KeymapRow(
    keymap: KeyMap,
    row: List<Keycap>,
    i:Int,
    isSelected: Boolean,
    keycapType: KeycapComponent,
    onClick: (Keycap) -> Unit,
    onChange: (KeyMap) -> Unit,
    keycapLimit: Int = 20,
    oneUSize: Int = 50) {
    val paddingBottom = oneUSize * row.fold(0f) { acc, keycap -> acc.coerceAtLeast(keycap.padding.bottom) }

    Row(
        modifier = Modifier
            .graphicsLayer(
                alpha = if (isSelected) 0f else 1f,
            )
            .wrapContentSize(unbounded = true)
            .padding(bottom = (paddingBottom + 10).dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    )
    {
        AddKeycapButton({ onChange(keymap.addKeycap(i)) }, row.size >= keycapLimit, oneUSize)
        DraggableList(
            items = row,
            key = { it },
            onDrop = {},
            direction = DraggableListDirection.horizontal
        ) { j, keycap, isSelected ->
            OneKeycap(
                keycap, i, j, isSelected, keycapType,
                { onClick(keycap) }, { onChange(keymap.deleteKeycap(i, j)) }, oneUSize
            )
        }
        RemoveRowButton({ onChange(keymap.removeRow(i)) }, oneUSize)
    }

}