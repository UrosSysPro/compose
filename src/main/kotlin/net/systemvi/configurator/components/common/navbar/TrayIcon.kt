package net.systemvi.configurator.components.common.navbar

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import arrow.core.None
import arrow.core.Option
import arrow.core.some
import net.systemvi.configurator.utils.services.AppStateService

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TrayIcon(link: NavbarLink,expanded: Boolean,onShowHoverCard:(Option<@Composable ()->Unit>)->Unit) {
    val appStateService= viewModel { AppStateService() }

    val selected=appStateService.currentPage==link.page

    val iconPadding by animateDpAsState(
        targetValue = if(expanded) 20.dp else 8.dp,
    )

    val backgroundColor by animateColorAsState(
        targetValue = if(selected)MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
    )

    val iconTint by animateColorAsState(
        targetValue = if(selected)MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
    )

    var hover by remember { mutableStateOf(false) }

    LaunchedEffect(selected) {
        if(selected) {onShowHoverCard(None)}
    }

    LaunchedEffect(hover) {
        if(hover && !selected) onShowHoverCard(link.hoverCard.some())
        if(hover &&  selected) onShowHoverCard(None)
    }

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
            .background(backgroundColor,RoundedCornerShape(15.dp))
            .combinedClickable{ appStateService.currentPage = link.page }
            .onPointerEvent(PointerEventType.Enter){hover=true}
            .onPointerEvent(PointerEventType.Exit){hover=false}
            .padding(iconPadding)
    ){
        Icon(link.icon,link.title, tint = iconTint)
    }
}