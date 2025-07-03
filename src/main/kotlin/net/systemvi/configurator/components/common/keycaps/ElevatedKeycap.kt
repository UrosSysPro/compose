package net.systemvi.configurator.components.common.keycaps

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import arrow.core.getOrElse
import net.systemvi.configurator.components.common.keyboard_grid.KeycapComponent
import net.systemvi.configurator.components.common.keyboard_grid.KeycapParam
import net.systemvi.configurator.components.tester.TesterPageViewModel
import net.systemvi.configurator.data.allKeys

val ElevatedKeycap: KeycapComponent = @Composable { param: KeycapParam ->
    val viewModel = viewModel { TesterPageViewModel() }
    val key = param.keycap.layers[0].getOrElse { allKeys.last() }
    val currentlyClicked = viewModel.currentlyDownKeys.contains(key)
    val wasClicked = viewModel.wasDownKeys.contains(key)

    val radius = 4.dp
    val elevation by animateDpAsState(targetValue = if (currentlyClicked) 2.dp else 8.dp)

    val textColor = when {
        wasClicked -> MaterialTheme.colorScheme.tertiary
        else -> MaterialTheme.colorScheme.primary
    }
    val containerColor = when {
        wasClicked -> MaterialTheme.colorScheme.tertiaryContainer
        else -> MaterialTheme.colorScheme.primaryContainer
    }

    LaunchedEffect(currentlyClicked) {
        val note = param.position.x + param.position.y * 12 + 40
        val velocity = 93
        if (currentlyClicked) {
            println(note)
            viewModel.channels?.get(0)?.noteOn(note, velocity)
        } else {
            viewModel.channels?.get(0)?.noteOff(note, velocity)
        }
    }

    Box(
        modifier = Modifier.padding(4.dp).fillMaxSize()

            .shadow(
                shape = RoundedCornerShape(radius),
                elevation = elevation,
                clip = false,
                ambientColor = MaterialTheme.colorScheme.secondary,
                spotColor = MaterialTheme.colorScheme.secondary
            )
            .background(
                shape = RoundedCornerShape(radius),
                color = containerColor,
            )
    ) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                key.name,
                style = MaterialTheme.typography.bodySmall,
                color = textColor
            )
        }
    }
}

