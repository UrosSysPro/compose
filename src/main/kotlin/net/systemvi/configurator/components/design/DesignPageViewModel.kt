package net.systemvi.configurator.components.design

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class DesignPageViewModel : ViewModel() {

    var showEdit by mutableStateOf(false)
}