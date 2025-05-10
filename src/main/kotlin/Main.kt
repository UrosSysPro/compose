import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import jssc.*
import jssc.SerialPort.*

data class Link(val url:String)


@Composable fun LinkCard(link:Link){
	Text(link.url)
}

@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }
	var checked by remember{ mutableStateOf(false) }


	
	val links=listOf(
		Link(url="link 1"),
		Link(url="link 2"),
		Link(url="link 3"),
		Link(url="link 4"),
		Link(url="link 5"),
	)
	
	var port by remember{ mutableStateOf<SerialPort?>(null) }
	var counter by remember{ mutableStateOf(0) }
    DisposableEffect(text){
		val ports=SerialPortList.getPortNames()
		for(port in ports){
			println(port)
		}
		port = SerialPort(ports[0])
		port?.openPort()
		port?.setParams(BAUDRATE_9600,  DATABITS_8, STOPBITS_1, PARITY_NONE)
		onDispose{
          port?.closePort()
		}
	}

    MaterialTheme {
		Scaffold(
			topBar={
				TopAppBar(
					title={
						Text("this is a title")
					}
				)
			},
			content={padding->
				/*Switch(
					checked=checked,
					onCheckedChange={
						checked=it
					}
				)*/
				Column{
					Text("$counter")		
					links.map{LinkCard(it)}
				}	
			}
		)
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
