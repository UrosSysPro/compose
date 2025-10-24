package net.systemvi.configurator.components.design.neo_design_page

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import net.systemvi.configurator.components.common.keyboard_grid.KeycapComponent
import net.systemvi.configurator.components.common.keyboard_grid.KeycapParam
import net.systemvi.configurator.components.common.keycaps.FlatKeycap
import net.systemvi.configurator.components.design.DesignPageViewModel

val FlatKeycap: KeycapComponent = @Composable {param: KeycapParam ->

    val viewModel = viewModel { DesignPageViewModel() }
    val keycap = param.keycap
    val selectedKeycaps = viewModel.selectedKeycaps.contains(keycap)

    FlatKeycap(selectedKeycaps, false, "A")
}