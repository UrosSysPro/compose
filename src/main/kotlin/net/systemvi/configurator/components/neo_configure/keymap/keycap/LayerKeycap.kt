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

@Composable
fun LayerKeycap(isDown: Boolean,  layer:Int) {

    val containerColor by animateColorAsState(
        targetValue = when {
            isDown -> MaterialTheme.colorScheme.tertiary
            else -> MaterialTheme.colorScheme.primaryContainer
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
                    LayerKeyColors[layer],
                ),
                shape = RoundedCornerShape(10.dp)
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "L${layer+1}",
            style = MaterialTheme.typography.bodySmall,
            color = LayerKeyColors[layer],
            textAlign = TextAlign.Center
        )
    }
}
