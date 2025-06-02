package net.systemvi.configurator
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import jssc.*
import jssc.SerialPort.*
import kotlinx.coroutines.*

@Composable fun SerialPortSelector(ports:List<String>,onSelect:(String?)->Unit){
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
			){
				Text("None")
			}
			ports.forEach {
				DropdownMenuItem(
					onClick = { onSelect(it) },
				){
					Text(it)
				}
			}
		}
	}
}

@Composable
@Preview
fun App() {
	var port by remember{ mutableStateOf<SerialPort?>(null) }
	var counter by remember{ mutableStateOf(0) }
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

	var a=mutableStateOf(2)
	var b=remember{2}

	MaterialTheme() {

		Scaffold(
			topBar={
				TopAppBar(
					title={
						Text("this is a title")
					}
				)
			},
			content={padding->
				SerialPortSelector(serialPorts,onSelect = {selectedPortName=it})
				Text("$counter")
			}
		)
	}
}

fun main() = application {
	Window(onCloseRequest = ::exitApplication) {
		App()
	}
}
