package net.systemvi.configurator.components.serial

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable fun SerialPortSelector(ports:List<String>,onSelect:(String?)->Unit){
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .padding(16.dp)
    ) {
        OutlinedButton(onClick = { expanded = !expanded }) {
            Text("Select Port")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                onClick = {onSelect(null)},
            ){
                Text("None")
            }
            ports.forEach {
                DropdownMenuItem(
                    onClick = { onSelect(it) },
                ){
                    Text(it)
                }
            }
        }
    }
}
