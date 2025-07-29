package net.systemvi.configurator.components.neo_configure.keymap.keycap

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
import net.systemvi.configurator.data.LayerKeyColors
import net.systemvi.configurator.data.SnapTapKeyColors

@Composable
fun SnapTapKeycap(isDown: Boolean, selected: Boolean, text:String, isFirst:Boolean, index:Int) {

    val containerColor by animateColorAsState(
        targetValue = when {
            isDown -> MaterialTheme.colorScheme.tertiary
            else -> MaterialTheme.colorScheme.primaryContainer
        }
    )

    val textColor by animateColorAsState(
        targetValue = when {
            isDown -> MaterialTheme.colorScheme.tertiaryContainer
            selected -> MaterialTheme.colorScheme.primaryContainer
            else -> MaterialTheme.colorScheme.primary
        }
    )

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
                    SnapTapKeyColors[index.coerceAtMost(SnapTapKeyColors.size)],
                ),
                shape = RoundedCornerShape(10.dp)
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = textColor,
            textAlign = TextAlign.Center
        )
    }
}
