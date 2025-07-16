package net.systemvi.configurator.components.tester.keycaps

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
import net.systemvi.configurator.components.common.keycaps.RGBWaveKeycap
import net.systemvi.configurator.components.tester.TesterPageViewModel
import net.systemvi.configurator.data.allKeys
import net.systemvi.configurator.utils.composables.rememberRainbowColor

val RGBWaveKeycap: KeycapComponent = @Composable {param: KeycapParam ->

    val viewModel = viewModel { TesterPageViewModel() }
    val key = param.keycap.layers[0].getOrElse { allKeys.last()}
    val currentlyClicked = viewModel.currentlyDownKeys.contains(key)
    val color = rememberRainbowColor(5000, param.position.x * 10)

    val containerColor by animateColorAsState(
        targetValue = when {
            currentlyClicked -> MaterialTheme.colorScheme.tertiary
            else -> MaterialTheme.colorScheme.primaryContainer
        }
    )

    viewModel.noteEffect(currentlyClicked, param.position.x + param.position.y * 12+24)

    RGBWaveKeycap(containerColor,color,key.name)
}

val RGBWaveKeycapName = @Composable {
    Text(
        "RGB Wave Keycap",
        color = rememberRainbowColor(5000)
    )
}

