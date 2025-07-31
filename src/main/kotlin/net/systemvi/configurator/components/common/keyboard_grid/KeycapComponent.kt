package net.systemvi.configurator.components.common.keyboard_grid

import androidx.compose.runtime.Composable
import net.systemvi.configurator.model.Keycap
import net.systemvi.configurator.model.KeycapPosition

data class KeycapParam(val keycap: Keycap, val position: KeycapPosition)

typealias KeycapComponent = @Composable (KeycapParam) -> Unit

typealias KeycapNameComponent = @Composable () -> Unit
