package net.systemvi.configurator.components.tester

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

val Keycap = @Composable { letter: String, clicked: Boolean ->
    val visible = letter !== ""
    if (visible)
        Box() {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        color =
                            if (clicked) MaterialTheme.colorScheme.primaryContainer
                            else MaterialTheme.colorScheme.tertiaryContainer
                    )
                    .border(
                        if (clicked) BorderStroke(
                            2.dp,
                            MaterialTheme.colorScheme.primary,

                            ) else BorderStroke(
                            0.dp,
                            MaterialTheme.colorScheme.tertiary
                        ),
                        shape = RoundedCornerShape(10.dp)
                    )
            ) {
                Text(letter, style = MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center)
            }
        }
}
