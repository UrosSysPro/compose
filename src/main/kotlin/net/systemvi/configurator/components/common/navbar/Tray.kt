package net.systemvi.configurator.components.common.navbar

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import arrow.core.Option

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
        links.forEachIndexed { index,link->
            TrayIcon(
                link=link,
                expanded=expanded,
                onShowHoverCard=onShowHoverCard
            )
            if(index != links.size - 1)Box(Modifier.width(5.dp)) {}
        }
    }

}