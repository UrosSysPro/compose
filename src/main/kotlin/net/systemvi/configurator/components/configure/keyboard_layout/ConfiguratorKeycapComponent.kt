package net.systemvi.configurator.components.configure.keyboard_layout

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import arrow.core.getOrElse
import net.systemvi.configurator.components.common.keyboard_grid.KeycapComponent
import net.systemvi.configurator.components.configure.ConfigureViewModel
import net.systemvi.configurator.model.Key

val ConfiguratorKeycapComponent: KeycapComponent=@Composable{params->
    val viewModel= viewModel { ConfigureViewModel() }
    val layer=viewModel.selectedLayer().coerceAtMost(params.keycap.layers.size-1)
    val pressed=viewModel.currentlyPressedKeycaps.contains(params.keycap.matrixPosition)
    val selected=viewModel.isKeycapSelected(params.position.y,params.position.x)
    val text=params.keycap.layers[layer].map { key:Key ->key.name }.getOrElse { "???" }
    val onClick={
        viewModel.selectKeycap(params.position.y,params.position.x)
    }

    val backgroundColor=when{
        pressed-> MaterialTheme.colorScheme.tertiaryContainer
        selected->MaterialTheme.colorScheme.primary
        else->MaterialTheme.colorScheme.primaryContainer
    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
//            .size((size1U*keycap.width.size).dp,50.dp)
            .padding(2.dp)
            .clip(RoundedCornerShape(10.dp))
            .combinedClickable(enabled = true, onClick = onClick)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(10.dp)
            )
            .border(
                BorderStroke(2.dp,MaterialTheme.colorScheme.secondary),
                shape = RoundedCornerShape(10.dp)
            )
    ){
        Text(
            text,
            fontSize = MaterialTheme.typography.bodySmall.fontSize,
        )
    }
}