package net.systemvi.configurator.components.keyboard_layout

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import net.systemvi.configurator.components.common.BorderHorizontal
import net.systemvi.configurator.components.common.BorderVertical


@Composable
fun KeyboardLayout() {
    Column {
        BorderHorizontal()
        Row{
            SidePanel()
            BorderVertical()
            Column{
                LayerSelector()
                BorderHorizontal()
                KeyboardLayoutGrid()
            }
        }
    }
}