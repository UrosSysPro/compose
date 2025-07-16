package net.systemvi.configurator.components

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import net.systemvi.configurator.model.*
import net.systemvi.configurator.utils.services.AppStateService

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun NavBar() {
    val appStateService=viewModel { AppStateService() }
    data class Link(val title:String,val page: Page,val icon: ImageVector)
    val links=listOf(
        Link("Configure", ConfigurePage, Icons.Filled.Warning),
        Link("Key Tester", TesterPage,Icons.Filled.Check),
        Link("Design", DesignPage,Icons.Filled.Create),
        Link("Settings", SettingsPage, Icons.Filled.Settings),
    )

    var mouseHover by remember { mutableStateOf(false) }

    val iconPadding by animateDpAsState(
        targetValue = if(mouseHover) 20.dp else 8.dp,
    )

    val trayElevation by animateDpAsState(
        targetValue = if(mouseHover) 10.dp else 5.dp
    )

    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ){
        Row(
            Modifier
                .padding(bottom = 30.dp)
                .shadow(
                    elevation = trayElevation,
                    shape = RoundedCornerShape(20.dp)
                )
                .background(
                    MaterialTheme.colorScheme.surface,
                    RoundedCornerShape(20.dp)
                )
                .padding(5.dp)
                .onPointerEvent(PointerEventType.Enter){mouseHover=true}
                .onPointerEvent(PointerEventType.Exit){mouseHover=false}
        ){
            links.forEach {
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(iconPadding)
                        .onClick{ appStateService.currentPage = it.page }
                ){
                    Icon(it.icon,it.title,)
//                    Text(it.title)
                }
            }
        }
    }
}