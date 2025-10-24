package net.systemvi.configurator.components.design.neo_design_page

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddKeycapButton(onClick: () -> Unit, disable: Boolean, size: Int) {
    FloatingActionButton(
        onClick = {
            if(!disable){
                onClick()
            }
        },
        containerColor = if(!disable) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceDim,
        contentColor = if(!disable) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface,
        modifier = Modifier
            .size(size.dp)
    ) {
        Icon(Icons.Filled.Add, contentDescription = "Add Keycap")
    }
}