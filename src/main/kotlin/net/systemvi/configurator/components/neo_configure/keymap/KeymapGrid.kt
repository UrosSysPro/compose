package net.systemvi.configurator.components.neo_configure.keymap

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import net.systemvi.configurator.components.common.keyboard_grid.Grid
import net.systemvi.configurator.components.neo_configure.NeoConfigureViewModel
import net.systemvi.configurator.components.neo_configure.keymap.keycap.NeoConfigKeycap
import net.systemvi.configurator.model.padding

@Composable
fun KeymapGrid(){
    val neoConfigViewModel = viewModel { NeoConfigureViewModel() }
    val keymap = neoConfigViewModel.keymap.getOrNull()!!

    Card {
        Column(
            modifier = Modifier
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            LayerSelector()
            Grid(
                keymap,
                NeoConfigKeycap,
                50,
            )
        }
    }
}