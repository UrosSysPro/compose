package net.systemvi.configurator.utils

import androidx.lifecycle.ViewModel
import arrow.core.None

class KeymapService : ViewModel() {
    val keymapApi = KeymapApi()

    fun onStart(){
        keymapApi.keymap = None
        println("keymap api service started")
    }

    fun onStop(){
        keymapApi.saveToDisk()
        println("keymap api service stopped")
    }
}