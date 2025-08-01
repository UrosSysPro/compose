package net.systemvi.configurator.components.neo_configure.key_selector

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import net.systemvi.configurator.model.Macro

@Composable
fun KeyCategoryMacro(
    macroKeys: List<Macro>,
    onAddMacro:(Macro)->Unit,
    onRemoveMacro:(Macro)->Unit,
    onMacroKeySelected:(Macro)->Unit,
) {
    ElevatedButton(
        onClick = {}
    ){
        Text(text = "+")
    }
    macroKeys.forEach { macro->
        ElevatedButton(
            onClick = { onMacroKeySelected(macro) },
        ){
            Row (
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(macro.name)
                IconButton(onClick = {  }) {
                    Icon(Icons.Filled.MoreVert, contentDescription = "three dots")
                }
            }
        }
    }
}