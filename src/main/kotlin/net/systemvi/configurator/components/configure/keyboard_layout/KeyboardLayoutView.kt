package net.systemvi.configurator.components.configure.keyboard_layout

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material3.*
import net.systemvi.configurator.components.common.BorderHorizontal
import net.systemvi.configurator.components.common.BorderVertical
import net.systemvi.configurator.components.configure.KeyboardLayoutPages


@Composable
fun KeyboardLayoutView() {
    var currentPage: KeyboardLayoutPages by remember { mutableStateOf(KeyboardLayoutPages.Keymap) }
    Column {
        BorderHorizontal()
        Row{
            SidePanel(currentPage) { page -> currentPage = page }
            BorderVertical()
            when(currentPage){
                KeyboardLayoutPages.Keymap->Column{
                    LayerSelector()
                    BorderHorizontal()
                    KeyboardLayoutGridView()
                }
                KeyboardLayoutPages.SaveAndLoad-> SaveAndLoad()
            }
        }
    }
}