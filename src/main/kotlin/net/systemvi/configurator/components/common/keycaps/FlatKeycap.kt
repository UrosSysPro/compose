package net.systemvi.configurator.components.common.keycaps

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

val FlatKeycap = @Composable { letter: String, wasClicked: Boolean, currentlyClicked: Boolean ->
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(2.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(
                color =
                    when {
                        currentlyClicked -> MaterialTheme.colorScheme.tertiary
                        wasClicked -> MaterialTheme.colorScheme.primary
                        else -> MaterialTheme.colorScheme.primaryContainer
                    }
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
            letter,
            style = MaterialTheme.typography.bodySmall,
            color =
                when {
                    currentlyClicked -> MaterialTheme.colorScheme.tertiaryContainer
                    wasClicked -> MaterialTheme.colorScheme.primaryContainer
                    else -> MaterialTheme.colorScheme.primary
                },
            textAlign = TextAlign.Center
        )
    }
}
