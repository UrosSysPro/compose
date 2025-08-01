package net.systemvi.configurator.components.neo_configure.keymap

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import arrow.core.getOrElse
import net.systemvi.configurator.components.common.keyboard_grid.Grid
import net.systemvi.configurator.components.neo_configure.NeoConfigureViewModel
import net.systemvi.configurator.components.neo_configure.keymap.keycap.NeoConfigKeycap
import net.systemvi.configurator.model.padding
import net.systemvi.configurator.model.width

@Composable
fun KeymapGrid(){
    val neoConfigViewModel = viewModel { NeoConfigureViewModel() }
    val keymap = neoConfigViewModel.keymap.getOrNull()!!
    val serialConnectionOpen=neoConfigViewModel.serialApi.map { it.connectionOpen }.getOrElse { false }

    Card {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .width(IntrinsicSize.Min),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                LayerSelector()
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    if(serialConnectionOpen) {
                        UploadButton()
                        SaveAsButton()
                    }
                    Text(keymap.name)
                }
            }
            Grid(
                keymap,
                NeoConfigKeycap,
                50,
            )
        }
    }
}