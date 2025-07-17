package net.systemvi.configurator
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import androidx.lifecycle.viewmodel.compose.viewModel
import net.systemvi.configurator.components.common.navbar.NavBar
import net.systemvi.configurator.components.configure.ConfigurePage
import net.systemvi.configurator.components.design.DesignPage
import net.systemvi.configurator.components.settings.SettingsPage
import net.systemvi.configurator.components.tester.TesterPage
import net.systemvi.configurator.components.title_bar.TitleBar
import net.systemvi.configurator.model.ConfigurePage
import net.systemvi.configurator.model.DesignPage
import net.systemvi.configurator.model.SettingsPage
import net.systemvi.configurator.model.TesterPage
import net.systemvi.configurator.utils.services.AppStateService
import net.systemvi.configurator.utils.services.KeymapService
import net.systemvi.configurator.utils.services.SerialApiService

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
@Preview
fun App(
	windowState: WindowState = rememberWindowState(),
	useClientDecoration:(Boolean)->Unit={}
) {

	val appStateService = viewModel { AppStateService() }
	val keymapService 	= viewModel { KeymapService() }
	val serialService 	= viewModel { SerialApiService() }

	DisposableEffect(Unit){
		appStateService.onStart()
		keymapService.onStart()
		serialService.onStart()
		onDispose {
			appStateService.onStart()
			keymapService.onStop()
			serialService.onStop()
		}
	}

	LaunchedEffect(appStateService.useClientDecoration){
		useClientDecoration(appStateService.useClientDecoration)
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

@OptIn(ExperimentalComposeUiApi::class)
fun main() = application {
	val windowState = rememberWindowState(
		width = 1600.dp,
		height = 900.dp,
	)

	var useClientDecoration by mutableStateOf(false)

	var windowDecoration: WindowDecoration by remember { mutableStateOf(
		if(useClientDecoration) WindowDecoration.Undecorated(3.dp) else WindowDecoration.SystemDefault
	)}

	var visible by remember { mutableStateOf(true) }

	LaunchedEffect(useClientDecoration) {
		if(useClientDecoration) {
			//change window to use client decoration
		}else{
			//change window to use defautl decoration
		}
	}

	Window(
		state = windowState,
		onCloseRequest = ::exitApplication,
		title = "Configurator",
		decoration = windowDecoration,
		visible = visible,
	) {
		Column(
			modifier = Modifier.fillMaxSize()
		) {
			if(useClientDecoration) TitleBar(
				onMinimize 	= { windowState.isMinimized=true },
				onMaximize 	= { windowState.placement = WindowPlacement.Maximized },
				onExit 		= { exitApplication() },
			)

			App(
				windowState,
				{useClientDecoration=it}
			)
		}
	}
}