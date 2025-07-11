package net.systemvi.configurator.components.configure.keyboard_keys.macro

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import arrow.core.None
import arrow.core.Option
import arrow.core.right
import arrow.core.some
import net.systemvi.configurator.model.Macro
import kotlin.collections.forEach

@Composable fun MacroRowItem(onClick:()->Unit,onEdit:()->Unit,macro: Macro){
    Row(Modifier.padding(8.dp)) {
        ElevatedButton(
            modifier = Modifier.padding(end = 5.dp),
            onClick = onClick
        ){
            Text(macro.name.ifBlank { "untitled" })
        }
        ElevatedButton(
            onClick = onEdit,
        ){
            Icon(Icons.Filled.Edit, contentDescription = "edit", tint = MaterialTheme.colorScheme.primary)
        }
    }
}

@Composable fun MacroRow(macros:List<Macro>,onClick:(Macro)->Unit,onEdit:(Option<Macro>)->Unit){
    FlowRow(

    ){
        ElevatedButton(
            onClick = { onEdit(None) }
        ){
            Icon(Icons.Filled.Add, "Add")
        }
        macros.forEach { macro ->
            MacroRowItem (
                onClick = {onClick(macro)},
                onEdit = {onEdit(macro.some())},
                macro
            )
        }
    }
}
