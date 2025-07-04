package net.systemvi.configurator.components.tester.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import net.systemvi.configurator.components.tester.TesterPageViewModel

@Composable
fun MuteSoundRow() {
    val viewModel = viewModel { TesterPageViewModel() }
    Row(
        modifier = Modifier.fillMaxWidth().height(75.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Mute Sound")
        Switch(
            checked = viewModel.muteOn,
            onCheckedChange = {
                viewModel.muteOn = it
                viewModel.focusRequester.requestFocus()
            },
            thumbContent = {
                if (viewModel.muteOn) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = "Mute On",
                        modifier = Modifier.size(SwitchDefaults.IconSize),
                    )
                } else {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Mute Off",
                        modifier = Modifier.size(SwitchDefaults.IconSize),
                    )
                }
            }
        )
    }
}