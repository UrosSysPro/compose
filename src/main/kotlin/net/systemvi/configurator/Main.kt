package net.systemvi.configurator
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import androidx.lifecycle.viewmodel.compose.viewModel
import net.systemvi.configurator.components.ConfigurePage
import net.systemvi.configurator.components.DesignPage
import net.systemvi.configurator.components.NavBar
import net.systemvi.configurator.components.ApplicationViewModel
import net.systemvi.configurator.components.SettingsPage
import net.systemvi.configurator.components.TesterPage
import net.systemvi.configurator.components.configure.ConfigurePage
import net.systemvi.configurator.components.configure.ConfigureViewModel
import net.systemvi.configurator.components.tester.TesterPage
import net.systemvi.configurator.components.settings.SettingsPage
import net.systemvi.configurator.components.design.DesignPage
import net.systemvi.configurator.utils.KeymapService
import net.systemvi.configurator.utils.SerialApiService


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App(appViewModel: ApplicationViewModel= viewModel { ApplicationViewModel() }) {

	val keymapService = viewModel { KeymapService() }
	val serialService = viewModel { SerialApiService() }

	DisposableEffect(Unit){
		keymapService.onStart()
		serialService.onStart()
		onDispose {
			keymapService.onStop()
			serialService.onStop()
		}
	}

	MaterialTheme (
		colorScheme = appViewModel.colorScheme
	){
		Scaffold(
			topBar={
				NavBar()
			},
			content={padding->
				when(appViewModel.currentPage){
					ConfigurePage -> ConfigurePage(Modifier.padding(padding))
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