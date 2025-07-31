package net.systemvi.configurator.components.tester.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import arrow.core.right
import net.systemvi.configurator.components.common.keyboard_grid.KeycapParam
import net.systemvi.configurator.components.tester.TesterPageViewModel
import net.systemvi.configurator.model.Key
import net.systemvi.configurator.model.Keycap
import net.systemvi.configurator.model.KeycapPosition

@Composable
fun KeycapSelectorRow() {
    val viewModel = viewModel { TesterPageViewModel() }
    val allKeycaps = viewModel.allKeycaps

    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth().height(75.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Choose Keycap")
        Box() {
            ElevatedButton(
                onClick = { expanded = !expanded },
            ) {
                Text("Keycaps")
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                    viewModel.focusRequester.requestFocus()
                },
            ) {
                for ((name, component) in allKeycaps) {
                    DropdownMenuItem(
                        leadingIcon = {
                            Box(modifier = Modifier.size(width = 50.dp, height = 50.dp)) {
                                component(
                                    KeycapParam(
                                        Keycap(listOf(Key(0.toByte(), "Q", 0).right())),
                                        KeycapPosition(0, 0)
                                    )
                                )
                            }
                        },
                        text = { name() },
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
}