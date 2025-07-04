package net.systemvi.configurator.utils

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color

@Composable fun rememberRainbowColor(durationMillis: Int, degOffset: Int=0): Color {
    val infiniteTransition = rememberInfiniteTransition(label = "infinite")
    val animatedFloat by infiniteTransition.animateFloat(
        initialValue = 360F,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "animatedFloat"
    )

    val color = Color.hsl(
        hue = (animatedFloat+degOffset.coerceAtLeast(0))%360,
        saturation = 1f,
        lightness = 0.5f
    )

    return color
}