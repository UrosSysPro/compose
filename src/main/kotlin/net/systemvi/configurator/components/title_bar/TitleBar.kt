package net.systemvi.configurator.components.title_bar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragData
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.FrameWindowScope
import javafx.scene.paint.Material
import net.systemvi.configurator.components.common.icons.Exit
import net.systemvi.configurator.components.common.icons.Maximize
import net.systemvi.configurator.components.common.icons.Minimize
import net.systemvi.configurator.model.height
import net.systemvi.configurator.model.padding

@Composable
fun FrameWindowScope.TitleBar(
    onMinimize: () -> Unit,
    onMaximize: () -> Unit,
    onExit: () -> Unit
) {
    val data=listOf(
        Pair(Minimize,onMinimize),
        Pair(Maximize,onMaximize),
        Pair(Exit,onExit),
    )


    WindowDraggableArea{
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .padding(bottom = 20.dp)
                .background(MaterialTheme.colorScheme.surface,RoundedCornerShape(50))
                .fillMaxWidth()
                .height(50.dp)
        ){
            data.forEach { (icon, onClick) ->
                IconButton(onClick = onClick){
                    Icon(icon, contentDescription = "Minimize")
                }
            }
        }
    }
}