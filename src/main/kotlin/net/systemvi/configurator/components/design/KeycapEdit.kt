package net.systemvi.configurator.components.design

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import arrow.optics.dsl.index
import net.systemvi.configurator.model.KeyMap
import net.systemvi.configurator.model.KeycapHeight
import net.systemvi.configurator.model.KeycapWidth
import net.systemvi.configurator.model.bottom
import net.systemvi.configurator.model.height
import net.systemvi.configurator.model.keycaps
import net.systemvi.configurator.model.left
import net.systemvi.configurator.model.padding
import net.systemvi.configurator.model.width

@Composable
fun <F>DropDownButton(list: List<F>, onSelect: (F) -> Unit) {
    var showDropdown by remember { mutableStateOf(false) }
    Box() {
        ElevatedButton(
            onClick = { showDropdown = true },
        ) {
            Text("${list[0]}")
        }
        DropdownMenu(
            expanded = showDropdown,
            onDismissRequest = { showDropdown = false }
        ) {
            for (item in list) {
                DropdownMenuItem(
                    text = { Text("$item") },
                    onClick = {onSelect(item); showDropdown = false }
                )
            }
        }
    }
}


@Composable
fun KeycapEdit( show: Boolean, keymap: KeyMap, x: Int, y: Int, onUpdate: (keymap: KeyMap) -> Unit ){
    if(show){
        Box(modifier = Modifier
            .height(300.dp)
            .width(300.dp)
            .background(
                color = MaterialTheme.colorScheme.primary,
            )
        ){
            Column(){
                Row(modifier = Modifier){
                    DropDownButton(KeycapWidth.entries, {
                       onUpdate (KeyMap.keycaps.index(x).index(y).width.set(keymap, it))
                    })
                    DropDownButton(KeycapHeight.entries, {
                        onUpdate (KeyMap.keycaps.index(x).index(y).height.set(keymap, it))
                    })
                }
                Row(modifier = Modifier){
                    DropDownButton(listOf(0.25f, 0.5f, 1f, 1.25f), {
                        onUpdate (KeyMap.keycaps.index(x).index(y).padding.left.set(keymap, it))
                    })
                    DropDownButton(listOf(0.25f, 0.5f, 1f, 1.25f), {
                        onUpdate (KeyMap.keycaps.index(x).index(y).padding.bottom.set(keymap, it))
                    })
                }
            }

        }
    }

}