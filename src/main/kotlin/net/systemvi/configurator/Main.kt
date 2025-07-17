package net.systemvi.configurator
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import net.systemvi.configurator.components.common.title_bar.TitleBar


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