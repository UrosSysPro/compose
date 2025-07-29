package net.systemvi.configurator.page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import net.systemvi.configurator.components.neo_configure.NeoConfigKeySelector
import net.systemvi.configurator.components.neo_configure.NeoConfigKeymap
import net.systemvi.configurator.components.neo_configure.NeoConfigKeymapSelector
import net.systemvi.configurator.components.neo_configure.NeoConfigPortSelector

@Composable
fun NeoConfigPage() {
    Column () {
        Row (){
            NeoConfigKeySelector()
            NeoConfigKeymapSelector()
            NeoConfigPortSelector()
        }
        NeoConfigKeymap()
    }
}