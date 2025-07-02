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
import net.systemvi.configurator.components.common.keycaps.ElevatedKeycap
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
    val passKey = alphabetKeys.last()

    fun processEvent(type: KeyEventType, key: Key) {
        println(key)
        when (type) {
            KeyEventType.KeyDown -> {
                viewModel.currentlyDownKeys += key
                viewModel.wasDownKeys += key
            }

            KeyEventType.KeyUp ->
                viewModel.currentlyDownKeys -= key
        }
    }

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
        Grid(defaultKeymaps()[2], ElevatedKeycap)
        ResetButton()
    }
    LaunchedEffect(Unit) {
        viewModel.focusRequester.requestFocus()
    }
}