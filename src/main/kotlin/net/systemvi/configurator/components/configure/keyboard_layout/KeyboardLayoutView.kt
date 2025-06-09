package net.systemvi.configurator.components.configure.keyboard_layout

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import net.systemvi.configurator.components.common.BorderHorizontal
import net.systemvi.configurator.components.common.BorderVertical


@Composable
fun KeyboardLayoutView() {
    Column {
        BorderHorizontal()
        Row{
            SidePanel()
            BorderVertical()
            Column{
                LayerSelector()
                BorderHorizontal()
                KeyboardLayoutGridView()
            }
        }
    }
}