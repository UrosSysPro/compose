package net.systemvi.configurator.components.tester

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.*
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import arrow.core.getOrElse
import arrow.core.toOption
import net.systemvi.configurator.components.common.keyboard_grid.Grid
import net.systemvi.configurator.components.common.keycaps.FlatKeycap
import net.systemvi.configurator.data.allKeys
import net.systemvi.configurator.data.alphabetKeys
import net.systemvi.configurator.data.defaultKeymaps
import net.systemvi.configurator.model.Key

@Composable
fun ResetButton() {
    val viewModel = viewModel { TesterPageViewModel() }
    Box(
        Modifier.size(250.dp, 50.dp),
        contentAlignment = Alignment.Center
    ) {
        ElevatedButton(
            onClick = { viewModel.resetKeys() },
            modifier = Modifier.fillMaxSize(),
        ) {
            Text("Reset")
        }
    }
}

@Composable fun TesterPage(modifier: Modifier) {

    val viewModel = viewModel { TesterPageViewModel() }

    fun processEvent(type: KeyEventType, key: Key) {
        println(key)
        when (type) {
            KeyEventType.KeyDown -> {
                viewModel.currentlyDownKeys += key
                viewModel.wasDownKeys += key
//                for (row in filteredItems) {
//                    for (item in row) {
//                        val currentKey = item.keycap.layers[0].getOrElse { passKey }
//                        if (currentKey.value == key.value) {
//                            viewModel.channels?.get(0)?.noteOn(item.x + item.y * 12, 93)
//                        }
//                    }
//                }
            }

            KeyEventType.KeyUp -> {
                viewModel.currentlyDownKeys -= key
//                for (row in filteredItems) {
//                    for (item in row) {
//                        val currentKey = item.keycap.layers[0].getOrElse { passKey }
//                        if (currentKey.value == key.value) {
//                            viewModel.channels?.get(0)?.noteOff(item.x + item.y * 12)
//                        }
//                    }
//                }
            }

        }
    }

    val passKey = alphabetKeys.last()

    val onKeyEvent: (KeyEvent) -> Boolean = { event ->
        if (event.type == KeyEventType.Unknown) {
            false
        } else {
            processEvent(
                event.type,
                allKeys.find { key -> key.nativeCode.toInt() == event.key.nativeKeyCode }.toOption().getOrElse {
                    println(event.key.nativeKeyCode)
                    passKey
                }
            )
            true
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .onKeyEvent(onKeyEvent)
            .focusRequester(viewModel.focusRequester)
            .focusable().then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

    ) {
        Grid(defaultKeymaps()[2], FlatKeycap)
        ResetButton()
    }
    LaunchedEffect(Unit) {
        viewModel.focusRequester.requestFocus()
    }
}