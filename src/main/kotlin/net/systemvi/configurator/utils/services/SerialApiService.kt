package net.systemvi.configurator.utils.services

import androidx.lifecycle.ViewModel
import net.systemvi.configurator.utils.api.KeyboardSerialApi

class SerialApiService : ViewModel() {

    val serialApi = KeyboardSerialApi()

    fun onStart(){
        println("serial api service started")
    }

    fun onStop(){
        println("serial api service stopped")
    }
}