package net.systemvi.configurator.components.common.keycaps

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import arrow.core.getOrElse
import net.systemvi.configurator.components.common.keyboard_grid.KeycapComponent
import net.systemvi.configurator.components.common.keyboard_grid.KeycapParam
import net.systemvi.configurator.components.tester.TesterPageViewModel
import net.systemvi.configurator.data.allKeys

val FlatKeycap: KeycapComponent = @Composable {param: KeycapParam ->

    val viewModel = viewModel { TesterPageViewModel() }
    val key = param.keycap.layers[0].getOrElse { allKeys.last()}
    val currentlyClicked = viewModel.currentlyDownKeys.contains(key)
    val wasClicked = viewModel.wasDownKeys.contains(key)

    val containerColor by animateColorAsState(
        targetValue = when {
            currentlyClicked -> MaterialTheme.colorScheme.tertiary
            wasClicked -> MaterialTheme.colorScheme.primary
            else -> MaterialTheme.colorScheme.primaryContainer
        }
    )

    val textColor by animateColorAsState(
        targetValue = when {
            currentlyClicked -> MaterialTheme.colorScheme.tertiaryContainer
            wasClicked -> MaterialTheme.colorScheme.primaryContainer
            else -> MaterialTheme.colorScheme.primary
        }
    )

    LaunchedEffect(currentlyClicked) {
        val note=param.position.x + param.position.y * 12+40
        val velocity=93
        if(currentlyClicked){
            println(note)
            viewModel.channels?.get(0)?.noteOn(note, velocity)
        }else{
            viewModel.channels?.get(0)?.noteOff(note, velocity)
        }
    }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(2.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(
                color = containerColor
            )
            .border(
                BorderStroke(
                    2.dp,
                    MaterialTheme.colorScheme.secondary,
                ),
                shape = RoundedCornerShape(10.dp)
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            key.name,
            style = MaterialTheme.typography.bodySmall,
            color = textColor,
            textAlign = TextAlign.Center
        )
    }
}

val FlatKeycapName = @Composable {
    Text("Flat Keycap")
}

