package net.systemvi.configurator.utils.modifier

import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

fun Modifier.dashedBorder(width:Float,color:Color,cornerRadius: CornerRadius): Modifier = composed {
    this.drawBehind {
        drawRoundRect(
            color = color,
            style = Stroke(
                width = width,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            ),
            cornerRadius = cornerRadius
        )
    }
}