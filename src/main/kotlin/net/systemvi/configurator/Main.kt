package net.systemvi.configurator
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import androidx.lifecycle.viewmodel.compose.viewModel
import jssc.*
import jssc.SerialPort.*
import net.systemvi.configurator.components.ConfigurePage
import net.systemvi.configurator.components.DesignPage
import net.systemvi.configurator.components.NavBar
import net.systemvi.configurator.components.PageViewModel
import net.systemvi.configurator.components.SettingsPage
import net.systemvi.configurator.components.TesterPage
import net.systemvi.configurator.components.configure.ConfigurePage
import net.systemvi.configurator.components.tester.TesterPage
import net.systemvi.configurator.components.settings.SettingsPage
import net.systemvi.configurator.components.design.DesignPage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App(pageViewModel: PageViewModel= viewModel { PageViewModel() }) {
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

	MaterialTheme {
		Scaffold(
			topBar={
				NavBar()
			},
			content={padding->
				when(pageViewModel.currentPage){
					ConfigurePage -> ConfigurePage(Modifier.padding(padding).fillMaxSize())
					TesterPage -> TesterPage(Modifier.padding(padding))
					DesignPage -> DesignPage(Modifier.padding(padding))
					SettingsPage -> SettingsPage(Modifier.padding(padding))
				}
			}
		)
	}
}

fun main() = application {
	val state=rememberWindowState(
		width = 1600.dp,
		height = 900.dp,
	)
	Window(
		state = state,
		onCloseRequest = ::exitApplication,
		title = "Configurator"
	) {
		App()
	}
}