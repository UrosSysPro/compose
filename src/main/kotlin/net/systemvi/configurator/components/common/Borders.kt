package net.systemvi.configurator.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BorderHorizontal(){
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(2.dp)
        .background(MaterialTheme.colorScheme.primary)
    )
}
@Composable
fun BorderVertical(){
    Box(modifier = Modifier
        .fillMaxHeight()
        .width(2.dp)
        .background(MaterialTheme.colorScheme.primary)
    )
}