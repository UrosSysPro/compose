package net.systemvi.configurator.components.design

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import net.systemvi.configurator.components.design.neo_design_page.tabs.DesignPageTabs
import net.systemvi.configurator.model.KeyMap
import net.systemvi.configurator.model.Keycap
import net.systemvi.configurator.model.KeycapPosition

class DesignPageViewModel : ViewModel() {

    var selectedKeycap: KeycapPosition? by mutableStateOf(null)
    var showSaveButton by mutableStateOf(false)
    var selectedKeycaps by mutableStateOf(emptyList<Keycap>())

    var keymap by mutableStateOf(KeyMap("", listOf(listOf())))
        private set

    fun updateKeymap(keymap: KeyMap) {
        this.keymap = keymap
    }

    var currentTab by mutableStateOf(DesignPageTabs.KeycapSettings)

}