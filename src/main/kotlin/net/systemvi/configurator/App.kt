package net.systemvi.configurator

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.rememberWindowState
import androidx.lifecycle.viewmodel.compose.viewModel
import net.systemvi.configurator.components.common.navbar.NavBar
import net.systemvi.configurator.components.configure.ConfigurePage
import net.systemvi.configurator.components.design.DesignPage
import net.systemvi.configurator.components.settings.SettingsPage
import net.systemvi.configurator.components.tester.TesterPage
import net.systemvi.configurator.components.serial_api_test_page.SerialApiTestPage
import net.systemvi.configurator.components.component_tester.ComponentPage
import net.systemvi.configurator.model.ComponentPage
import net.systemvi.configurator.model.ConfigurePage
import net.systemvi.configurator.model.DesignPage
import net.systemvi.configurator.model.SerialApiTestPage
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
            appStateService.onStop()
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
                        ComponentPage -> ComponentPage(Modifier)
                        SerialApiTestPage -> SerialApiTestPage(Modifier)
                    }
                    NavBar()
                }
            }
        )
    }
}
