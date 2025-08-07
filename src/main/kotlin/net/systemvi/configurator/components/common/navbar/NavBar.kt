package net.systemvi.configurator.components.common.navbar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import arrow.core.None
import arrow.core.Option
import net.systemvi.configurator.components.settings.SettingsHoverCard
import net.systemvi.configurator.components.tester.TesterHoverCard
import net.systemvi.configurator.page.ComposablesGalleryPage
import net.systemvi.configurator.page.ConfigurePage
import net.systemvi.configurator.page.DesignPage
import net.systemvi.configurator.page.NeoConfigurePage
import net.systemvi.configurator.page.SettingsPage
import net.systemvi.configurator.page.TesterPage
import net.systemvi.configurator.utils.annotations.ComposablesGalleryItem

@ComposablesGalleryItem("nav bar")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun NavBar() {
    val links=listOf(
//        NavbarLink("Configure", ConfigurePage, Icons.Filled.Warning,   {}),
        NavbarLink("Neo Configure", NeoConfigurePage, Icons.Filled.Warning,   {}),
        NavbarLink("Key Tester", TesterPage,    Icons.Filled.Check,     { TesterHoverCard() }),
        NavbarLink("Design", DesignPage,    Icons.Filled.Create,    {}),
        NavbarLink("Settings", SettingsPage,  Icons.Filled.Settings,  { SettingsHoverCard() }),
        NavbarLink("Composables Gallery", ComposablesGalleryPage,  Icons.Filled.Settings,  {}),
//        NavbarLink("Component",     ComponentPage,  Icons.Filled.Settings, {}),
//        NavbarLink("Serial Api Tester", SerialApiTestPage,  Icons.Filled.Settings, {}),
    )

    var mouseHover by remember { mutableStateOf(false) }

    var hoverCard: Option<@Composable ()->Unit> by remember { mutableStateOf(None) }

    LaunchedEffect(mouseHover) {
        if(!mouseHover) {hoverCard=None}
    }

    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .onPointerEvent(PointerEventType.Enter){mouseHover=true}
                .onPointerEvent(PointerEventType.Exit){mouseHover=false}
        ) {
            hoverCard.onSome { hover -> hover() }
            Tray(mouseHover,links,{hoverCard=it})
        }
    }
}