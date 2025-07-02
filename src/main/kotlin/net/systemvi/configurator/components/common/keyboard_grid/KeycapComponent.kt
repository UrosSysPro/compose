package net.systemvi.configurator.components.common.keyboard_grid

import androidx.compose.runtime.Composable
import net.systemvi.configurator.components.configure.KeycapPosition
import net.systemvi.configurator.model.Keycap

data class KeycapParam(val keycap: Keycap, val position: KeycapPosition)

typealias KeycapComponent = @Composable (KeycapParam) -> Unit
