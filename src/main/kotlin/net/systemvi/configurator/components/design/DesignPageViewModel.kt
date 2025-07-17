package net.systemvi.configurator.components.design

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import net.systemvi.configurator.components.configure.KeycapPosition
import net.systemvi.configurator.model.KeyMap

class DesignPageViewModel : ViewModel() {

    var keymap by mutableStateOf(KeyMap("Untitled 1", listOf(listOf())))
    var selectedKeycap: KeycapPosition? by mutableStateOf(null)
}