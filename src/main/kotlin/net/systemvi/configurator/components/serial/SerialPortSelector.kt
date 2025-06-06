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
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .padding(16.dp)
    ) {
        OutlinedButton(onClick = {
            configureViewModel.readPortNames()
            expanded = !expanded
        }) {
            Text("Select Port")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                onClick = {configureViewModel.selectPort(null); expanded = false},
                text = { Text("None") }
            )
            configureViewModel.serialPortNames.forEach {
                DropdownMenuItem(
                    onClick = { configureViewModel.selectPort(it);expanded = false },
                    text = { Text(it) }
                )
            }
        }
    }
}
