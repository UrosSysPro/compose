package net.systemvi.configurator.components.common.keymap_preview

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.systemvi.configurator.components.common.keyboard_grid.Grid
import net.systemvi.configurator.components.common.keyboard_grid.KeycapComponent
import net.systemvi.configurator.model.KeyMap
import net.systemvi.configurator.model.KeymapType

val keymapPreviewKeycap: KeycapComponent = @Composable { param ->
    Box(
       modifier = Modifier
           .padding(0.5.dp)
           .fillMaxSize()
           .background(
               MaterialTheme.colorScheme.onPrimaryContainer,
               RoundedCornerShape(2.dp)
           )
    ) {}
}

@Composable
fun KeymapPreview(keymap: KeyMap,onclick:(KeyMap)->Unit){
    Card (
        modifier = Modifier,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp,
        ),
    ){
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .combinedClickable{
                    onclick(keymap)
                }
                .size(400.dp,100.dp)
                .padding(16.dp)
        ){
            Column (
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                Grid(
                    keymap,
                    keymapPreviewKeycap,
                    (150f/(keymap.keycaps[0].size.toFloat()+3)).toInt()
                )
            }
            Text(
                keymap.name,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontSize = 18.sp,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = when(val type=keymap.type){
                        KeymapType.Saved -> ""
                        KeymapType.Default -> "Demo"
                        is KeymapType.Onboard -> "Up to date"
                    },
                modifier = Modifier.weight(0.3f),
                fontSize = 10.sp
            )
        }
    }
}