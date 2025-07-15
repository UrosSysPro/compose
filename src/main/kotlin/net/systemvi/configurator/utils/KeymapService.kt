package net.systemvi.configurator.utils

import androidx.lifecycle.ViewModel

class KeymapService : ViewModel() {
    val keymapApi = KeymapApi()

    fun onStart(){
        println("keymap api service started")
    }

    fun onStop(){
        println("keymap api service stopped")
    }
}