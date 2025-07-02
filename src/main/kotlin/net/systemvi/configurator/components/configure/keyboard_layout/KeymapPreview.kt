package net.systemvi.configurator.components.configure.keyboard_layout

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import net.systemvi.configurator.components.configure.ConfigureViewModel
import net.systemvi.configurator.components.common.keyboard_grid.Grid
import net.systemvi.configurator.model.KeyMap
import net.systemvi.configurator.model.height

@Composable fun KeymapPreview(keymap: KeyMap){
    val viewModel= viewModel { ConfigureViewModel() }
    Card(
        modifier = Modifier
            .clipToBounds()
            .padding(end = 10.dp)
            .height(200.dp)
            .width(IntrinsicSize.Min)
            .clickable(onClick = {viewModel.keymapLoad(keymap)})
        ,
    ) {
       Column(
           horizontalAlignment = Alignment.CenterHorizontally,
           modifier = Modifier.fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 20.dp)
       ){
           Text(
               keymap.name,
               modifier = Modifier.fillMaxWidth(),
               fontWeight = FontWeight.Bold,
           )
           Column(
               verticalArrangement = Arrangement.Center,
               horizontalAlignment = Alignment.CenterHorizontally,
               modifier = Modifier.weight(1f).fillMaxWidth()
           ) {
               Grid(keymap,KeyboardPreviewKeycapComponent,15)
           }
       }
    }
}