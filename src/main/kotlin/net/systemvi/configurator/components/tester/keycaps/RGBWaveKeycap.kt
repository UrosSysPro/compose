package net.systemvi.configurator.components.tester.keycaps

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import arrow.core.getOrElse
import net.systemvi.configurator.components.common.keyboard_grid.KeycapComponent
import net.systemvi.configurator.components.common.keyboard_grid.KeycapParam
import net.systemvi.configurator.components.common.keycaps.RGBWaveKeycap
import net.systemvi.configurator.components.tester.TesterPageViewModel
import net.systemvi.configurator.data.allKeys
import net.systemvi.configurator.utils.composables.rememberRainbowColor

val RGBWaveKeycap: KeycapComponent = @Composable {param: KeycapParam ->

    val viewModel = viewModel { TesterPageViewModel() }
    val key = param.keycap.layers[0].getOrElse { allKeys.last()}
    val currentlyClicked = viewModel.currentlyDownKeys.contains(key)
    val offset=param.position.row * 10

    viewModel.noteEffect(currentlyClicked, param.position.row + param.position.column * 12+24)

    RGBWaveKeycap(currentlyClicked,offset,key.name)
}

val RGBWaveKeycapName = @Composable {
    Text(
        "RGB Wave Keycap",
        color = rememberRainbowColor(5000)
    )
}

