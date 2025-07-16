package net.systemvi.configurator.utils.services

import androidx.lifecycle.ViewModel
import net.systemvi.configurator.utils.api.KeymapApi

class KeymapService : ViewModel() {
    val keymapApi = KeymapApi()

    fun onStart(){
        keymapApi.loadFromDisk()
        println("keymap api service started")
    }

    fun onStop(){
        keymapApi.saveToDisk()
        println("keymap api service stopped")
    }
}