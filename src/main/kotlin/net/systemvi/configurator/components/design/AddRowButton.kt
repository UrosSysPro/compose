package net.systemvi.configurator.components.design

import androidx.compose.foundation.layout.padding
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
fun AddRowButton(onClick: () -> Unit, disable: Boolean) {
    FloatingActionButton(
        onClick = {
            if(!disable) {
                onClick()
            }
        },
        containerColor = if(!disable) MaterialTheme.colorScheme.tertiaryContainer else MaterialTheme.colorScheme.surfaceDim,
        contentColor = if(!disable) MaterialTheme.colorScheme.onTertiaryContainer else MaterialTheme.colorScheme.onSurface,
        modifier = Modifier
            .padding(vertical = 10.dp)
            .size(50.dp)
    ) {
        Icon(Icons.Filled.Add, contentDescription = "Add Row")
    }
}