package net.systemvi.configurator
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import androidx.lifecycle.viewmodel.compose.viewModel
import net.systemvi.configurator.components.NavBar
import net.systemvi.configurator.components.configure.ConfigurePage
import net.systemvi.configurator.components.tester.TesterPage
import net.systemvi.configurator.components.settings.SettingsPage
import net.systemvi.configurator.components.design.DesignPage
import net.systemvi.configurator.model.*
import net.systemvi.configurator.utils.services.AppStateService
import net.systemvi.configurator.utils.services.KeymapService
import net.systemvi.configurator.utils.services.SerialApiService


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {

	val keymapService = viewModel { KeymapService() }
	val serialService = viewModel { SerialApiService() }
	val appStateService=viewModel { AppStateService() }

	DisposableEffect(Unit){
		appStateService.onStart()
		keymapService.onStart()
		serialService.onStart()
		onDispose {
			appStateService.onStop()
			keymapService.onStop()
			serialService.onStop()
		}
	}

	MaterialTheme (
		colorScheme = appStateService.colorScheme
	){
		Scaffold(
			content={padding->
				Box(Modifier.padding(padding).fillMaxSize()){
					when(appStateService.currentPage){
						ConfigurePage -> ConfigurePage(Modifier)
						TesterPage -> TesterPage(Modifier)
						DesignPage -> DesignPage(Modifier)
						SettingsPage -> SettingsPage(Modifier)
					}
					NavBar()
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