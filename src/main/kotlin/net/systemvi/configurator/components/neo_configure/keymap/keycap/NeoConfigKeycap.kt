package net.systemvi.configurator.components.neo_configure.keymap.keycap

import androidx.compose.runtime.Composable
import arrow.core.getOrElse
import net.systemvi.configurator.components.common.keyboard_grid.KeycapComponent
import net.systemvi.configurator.components.common.keycaps.FlatKeycap

val NeoConfigKeycap: KeycapComponent = { params ->
    FlatKeycap(false,false,params.keycap.layers[0].map { it.name }.getOrElse { "???" })
}