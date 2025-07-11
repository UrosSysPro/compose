package net.systemvi.configurator.components.configure.keyboard_keys.macro

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import arrow.core.None
import arrow.core.Option
import arrow.core.some
import net.systemvi.configurator.model.Macro
import kotlin.collections.forEach

@Composable fun MacroRowItem(content:@Composable () -> Unit){
    Box(Modifier.padding(8.dp)) {
        content()
    }
}

@Composable fun MacroRow(macros:List<Macro>,onClick:(Option<Macro>)->Unit){
    FlowRow(

    ){
        MacroRowItem {
            ElevatedButton(
                onClick = { onClick(None) }
            ){
                Icon(Icons.Filled.Add, "Add")
            }
        }
        macros.forEach { macro ->
            MacroRowItem {
                ElevatedButton(
                    onClick = {onClick(macro.some())}
                ){
                    Text(macro.name)
                }
            }
        }
    }
}
