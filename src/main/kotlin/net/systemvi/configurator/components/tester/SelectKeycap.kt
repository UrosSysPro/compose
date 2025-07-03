package net.systemvi.configurator.components.tester

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import arrow.core.right
import net.systemvi.configurator.components.common.keyboard_grid.KeycapParam
import net.systemvi.configurator.components.configure.KeycapPosition
import net.systemvi.configurator.data.allKeys
import net.systemvi.configurator.model.Keycap
import androidx.compose.ui.Modifier

@Composable
fun KeycapSelector() {
    val viewModel = viewModel { TesterPageViewModel() }
    val allKeycaps = viewModel.allKeycaps

    var expanded by remember { mutableStateOf(false) }
    Box() {
        ElevatedButton(
            onClick = { expanded = !expanded },
        ) {
            Text("Choose Keycap")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            for ((name, component) in allKeycaps) {
                DropdownMenuItem(
                    leadingIcon = {
                        Box(modifier = Modifier.size(width = 50.dp, height = 50.dp)) {
                            component(
                                KeycapParam(
                                    Keycap(listOf(allKeys[0].right())),
                                    KeycapPosition(0, 0)
                                )
                            )
                        }
                    },
                    text = { Text(name) },
                    onClick = {
                        viewModel.selectedKeycap = component
                        viewModel.focusRequester.requestFocus()
                        expanded = false
                    },
                )
            }
        }
    }
}