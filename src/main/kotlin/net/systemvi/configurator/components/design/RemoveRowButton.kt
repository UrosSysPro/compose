package net.systemvi.configurator.components.design

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RemoveRowButton(onClick: () -> Unit) {
    ElevatedButton(
        onClick = onClick,
        modifier = Modifier
            .padding(start = 10.dp)
    ) {
        Text(
            "Remove Row",
            style = MaterialTheme.typography.bodySmall
        )
    }
}
