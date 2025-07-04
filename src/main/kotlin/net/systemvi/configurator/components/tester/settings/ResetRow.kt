package net.systemvi.configurator.components.tester.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import net.systemvi.configurator.components.tester.TesterPageViewModel

@Composable
fun ResetRow() {
    val viewModel = viewModel { TesterPageViewModel() }
    Row(
        modifier = Modifier.fillMaxWidth().height(75.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Reset All Keys")
        ElevatedButton(
            onClick = {
                viewModel.resetKeys()
                viewModel.focusRequester.requestFocus()
            },
        ) {
            Text("Reset")
        }
    }
}