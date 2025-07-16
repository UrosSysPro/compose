package net.systemvi.configurator.components.common.keycaps

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import arrow.core.getOrElse
import net.systemvi.configurator.components.common.keyboard_grid.KeycapComponent
import net.systemvi.configurator.components.common.keyboard_grid.KeycapParam
import net.systemvi.configurator.components.tester.TesterPageViewModel
import net.systemvi.configurator.data.allKeys

@Composable
fun ElevatedKeycap(isDown:Boolean,wasDown:Boolean,text:String) {

    val radius = 4.dp
    val elevation by animateDpAsState(targetValue = if (isDown) 2.dp else 8.dp)

    val textColor = when {
        wasDown -> MaterialTheme.colorScheme.tertiary
        else -> MaterialTheme.colorScheme.primary
    }
    val containerColor = when {
        wasDown -> MaterialTheme.colorScheme.tertiaryContainer
        else -> MaterialTheme.colorScheme.primaryContainer
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
                text = text,
                style = MaterialTheme.typography.bodySmall,
                color = textColor
            )
        }
    }
}