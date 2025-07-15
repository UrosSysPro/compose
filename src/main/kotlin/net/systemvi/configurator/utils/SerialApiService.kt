package net.systemvi.configurator.utils

import androidx.lifecycle.ViewModel

class SerialApiService : ViewModel() {

    val serialApi = KeyboardSerialApi()

    fun onStart(){
        println("serial api service started")
    }

    fun onStop(){
        println("serial api service stopped")
    }
}