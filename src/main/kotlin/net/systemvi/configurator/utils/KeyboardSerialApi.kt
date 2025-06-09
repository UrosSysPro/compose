package net.systemvi.configurator.utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import jssc.SerialPort

object KeyboardSerialApi {
    var serialPortNames by mutableStateOf(listOf<String>())
    var selectedPortName by mutableStateOf<String?>(null)
    var port by mutableStateOf<SerialPort?>(null)
    var messageBuffer=listOf<Byte>()

}