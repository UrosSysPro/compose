package net.systemvi.configurator.components.design

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import net.systemvi.configurator.components.configure.KeycapPosition

class DesignPageViewModel : ViewModel() {

    var selectedKeycap: KeycapPosition? by mutableStateOf(null)
    var showSaveButton by mutableStateOf(false)
}