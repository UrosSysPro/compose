package net.systemvi.configurator.components.design

import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun AddRowButton(onClick: () -> Unit) {
    ElevatedButton(
        onClick = onClick,
    ) {
        Text(
            "Add Row",
            style = MaterialTheme.typography.bodySmall
        )
    }
}