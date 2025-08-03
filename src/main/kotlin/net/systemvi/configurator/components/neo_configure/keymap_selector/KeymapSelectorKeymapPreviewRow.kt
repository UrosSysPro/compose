package net.systemvi.configurator.components.neo_configure.keymap_selector

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.systemvi.configurator.model.KeyMap

@Composable
fun KeymapRow(title:String,keymaps: List<KeyMap>,columns:Int = 2, onclick: (keymap: KeyMap) -> Unit) {

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        FlowRow(
            modifier = Modifier
                .padding(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            maxItemsInEachRow = columns,

        ){
            keymaps.forEach {
                net.systemvi.configurator.components.common.keymap_preview.KeymapPreview(it,{onclick(it)})
            }
        }
    }
}
