package net.systemvi.configurator.components.common.navbar

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import arrow.core.Option
import net.systemvi.configurator.utils.services.AppStateService

@Composable
fun Tray(expanded:Boolean,links:List<NavbarLink>,onShowHoverCard:(Option<@Composable ()->Unit>)->Unit) {

    val trayElevation by animateDpAsState(
        targetValue = if(expanded) 10.dp else 5.dp
    )

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
    ){
        links.forEach {
            TrayIcon(
                link=it,
                expanded=expanded,
                onShowHoverCard=onShowHoverCard
            )
        }
    }

}