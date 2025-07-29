package net.systemvi.configurator.components.neo_configure.keymap

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.systemvi.configurator.utils.modifier.dashedBorder

@Composable
fun PleaseSelectKeymapOrPort(){
    Column(
        modifier = Modifier
            .dashedBorder(
                width=4f,
                color= MaterialTheme.colorScheme.secondary,
                cornerRadius=CornerRadius(20f)
            )
            .size(800.dp,400.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text="Select keymap or port",
            color = MaterialTheme.colorScheme.secondary,
            fontWeight = FontWeight.Medium,
            fontSize = 24.sp,
        )
    }
}