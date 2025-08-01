package net.systemvi.configurator.components.neo_configure.keymap_selector

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.systemvi.configurator.components.configure.keyboard_layout.KeymapPreview
import net.systemvi.configurator.model.KeyMap

@Composable
fun KeymapRow(title:String,keymaps: List<KeyMap>, onclick: (keymap: KeyMap) -> Unit) {

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(text = title)
        FlowRow(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ){
            keymaps.forEach {
                KeymapPreview(it,{onclick(it)})
            }
        }
    }
}
