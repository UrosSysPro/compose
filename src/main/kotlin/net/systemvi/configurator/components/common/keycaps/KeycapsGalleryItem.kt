package net.systemvi.configurator.components.common.keycaps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.systemvi.configurator.components.tester.keycaps.FlatKeycap
import net.systemvi.configurator.utils.annotations.ComposablesGalleryItem

@ComposablesGalleryItem("Keycaps")
@Composable
fun KeycapsGalleryItem(){
    val size=50.dp
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Box(Modifier.size(size)) { FlatKeycap(false,false,"A") }
        Box(Modifier.size(size)) { ElevatedKeycap(false,false,"A") }
        Box(Modifier.size(size)) { RGBWaveKeycap(false,0,"A") }
    }
}