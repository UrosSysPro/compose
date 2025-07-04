package net.systemvi.configurator.components.tester

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.*
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import arrow.core.getOrElse
import arrow.core.toOption
import net.systemvi.configurator.components.common.keyboard_grid.Grid
import net.systemvi.configurator.components.tester.settings.BottomSheetV1
import net.systemvi.configurator.data.allKeys
import net.systemvi.configurator.data.alphabetKeys
import net.systemvi.configurator.data.defaultKeymaps
import net.systemvi.configurator.model.Key


@Composable fun TesterPage(modifier: Modifier) {

    val viewModel = viewModel { TesterPageViewModel() }
    val passKey = alphabetKeys.last()
    val offset by animateFloatAsState(
        targetValue = if (viewModel.showBottomSheet) -200f else 0f
    )

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

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Settings")},
                icon = { Icon(Icons.Filled.Settings, "") },
                onClick = { viewModel.showBottomSheet = true }
            )
        }
    ){
        Column(
            modifier = Modifier.fillMaxSize()
                .onKeyEvent(onKeyEvent)
                .focusRequester(viewModel.focusRequester)
                .focusable().then(modifier),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Box(modifier = Modifier.offset(0.dp, offset.dp)){
                Grid(defaultKeymaps()[2], viewModel.selectedKeycap)
            }
            BottomSheetV1()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.focusRequester.requestFocus()
    }
}