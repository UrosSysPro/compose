package net.systemvi.configurator.components.serial_api_test_page

import androidx.compose.foundation.layout.Column
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import net.systemvi.configurator.utils.services.SerialApiService

@Composable
fun SerialApiTestPage(modifier: Modifier = Modifier) {
    val serialApi = viewModel { SerialApiService() }.serialApi
    val portNames = serialApi.getPortNames()
    Text("test page")
    Column() {
        portNames.forEach { portName ->
            OutlinedButton(onClick = { serialApi.selectPort(portName) }) {
                Text(text = portName)
            }
        }
        OutlinedButton(onClick = { serialApi.loadFromFlash() }) {
            Text("load")
        }
        OutlinedButton(onClick = { serialApi.storeToFlash() }) {
            Text("store")
        }
        OutlinedButton(onClick = { serialApi.formatFlash() }) {
            Text("format")
        }
        OutlinedButton(onClick = { serialApi.closePort() }) {
            Text("close port")
        }
    }

}