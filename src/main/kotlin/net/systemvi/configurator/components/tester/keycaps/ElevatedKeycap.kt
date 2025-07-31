package net.systemvi.configurator.components.tester.keycaps

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import arrow.core.getOrElse
import net.systemvi.configurator.components.common.keyboard_grid.KeycapComponent
import net.systemvi.configurator.components.common.keyboard_grid.KeycapParam
import net.systemvi.configurator.components.common.keycaps.ElevatedKeycap
import net.systemvi.configurator.components.tester.TesterPageViewModel
import net.systemvi.configurator.data.allKeys

val ElevatedKeycap: KeycapComponent = @Composable { param: KeycapParam ->
    val viewModel = viewModel { TesterPageViewModel() }
    val key = param.keycap.layers[0].getOrElse { allKeys.last() }
    val currentlyClicked = viewModel.currentlyDownKeys.contains(key)
    val wasClicked = viewModel.wasDownKeys.contains(key)

    viewModel.noteEffect(currentlyClicked, param.position.row + param.position.column * 12+24)

    ElevatedKeycap(currentlyClicked,wasClicked,key.name)
}

val ElevatedKeycapName = @Composable {
    Text("Elevated Keycap")
}

