package net.systemvi.configurator.components.serial

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import jssc.SerialPort
import jssc.SerialPort.BAUDRATE_9600
import jssc.SerialPort.DATABITS_8
import jssc.SerialPort.PARITY_NONE
import jssc.SerialPort.STOPBITS_1
import jssc.SerialPortList
import net.systemvi.configurator.components.configure.ConfigureViewModel

@Composable fun SerialPortSelector(configureViewModel: ConfigureViewModel= viewModel { ConfigureViewModel() }) {
    val onSelect:(String?)->Unit={}

    var port by remember{ mutableStateOf<SerialPort?>(null) }
    var serialPorts by remember{ mutableStateOf(listOf<String>())}
    var selectedPortName by remember{ mutableStateOf<String?>(null)}

    LaunchedEffect(null) {
        val ports=SerialPortList.getPortNames()
        serialPorts = ports.toList()
        serialPorts.forEach { println(it) }
    }

    DisposableEffect(selectedPortName){
        if(selectedPortName != null){
            if(port?.isOpened == true)port?.closePort()
            port = SerialPort(selectedPortName)
            port?.openPort()
            port?.setParams(BAUDRATE_9600,  DATABITS_8, STOPBITS_1, PARITY_NONE)
            port?.addEventListener {
                println(it)
            }
        }
        onDispose{
            if(port?.isOpened == true)port?.closePort()
        }
    }

    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .padding(16.dp)
    ) {
        OutlinedButton(onClick = { expanded = !expanded }) {
            Text("Select Port")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                onClick = {onSelect(null)},
                text = { Text("None") }
            )
            serialPorts.forEach {
                DropdownMenuItem(
                    onClick = { onSelect(it) },
                    text = { Text(it) }
                )
            }
        }
    }
}
