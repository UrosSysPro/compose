package net.systemvi.configurator.components.common.keycaps

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import arrow.core.getOrElse
import net.systemvi.configurator.components.common.keyboard_grid.KeycapComponent
import net.systemvi.configurator.components.common.keyboard_grid.KeycapParam
import net.systemvi.configurator.components.tester.TesterPageViewModel
import net.systemvi.configurator.data.allKeys

val RGBWaveKeycap: KeycapComponent = @Composable {param: KeycapParam ->

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

    val infiniteTransition = rememberInfiniteTransition(label = "infinite")
    val animatedFloat by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, delayMillis = param.position.x * 50, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "animatedFloat"
    )

    val color = Color.hsl(
        hue = animatedFloat,
        saturation = 1f,
        lightness = 0.5f
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
                    width = 2.dp,
                    color = color,
                ),
                shape = RoundedCornerShape(10.dp)
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            key.name,
            style = MaterialTheme.typography.bodySmall,
            color = color,
            textAlign = TextAlign.Center
        )
    }
}

val RGBWaveKeycapName = @Composable {
    val infiniteTransition = rememberInfiniteTransition(label = "infinite")
    val animatedFloat by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "animatedFloat"
    )

    val color = Color.hsl(
        hue = animatedFloat,
        saturation = 1f,
        lightness = 0.5f
    )

    Text(
        "RGB Wave Keycap",
        color = color
    )
}

