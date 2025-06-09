package net.systemvi.configurator.components.tester

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign

val Keycap = @Composable { letter: String, clicked: Boolean ->
    val visible = letter !== ""
    if(visible)
    Box() {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(10.dp))
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer
                )
                .border(
                    if (clicked) BorderStroke(
                        2.dp,
                        MaterialTheme.colorScheme.secondary,

                    ) else BorderStroke(
                        0.dp,
                        Color(0, 0, 0, 0)
                    ),
                    shape = RoundedCornerShape(10.dp)
                )
        ) {
            Text(letter, style = MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center)
        }
    }
}
