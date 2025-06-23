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

enum class CurrentPage(val title:String){
    Keymap("Keymap"),
    SaveAndLoad("Save and Load");
}

@Composable
fun KeyboardLayoutView() {
    var currentPage: CurrentPage by remember { mutableStateOf(CurrentPage.Keymap) }
    Column {
        BorderHorizontal()
        Row{
            SidePanel(currentPage) { page -> currentPage = page }
            BorderVertical()
            when(currentPage){
                CurrentPage.Keymap->Column{
                    LayerSelector()
                    BorderHorizontal()
                    KeyboardLayoutGridView()
                }
                CurrentPage.SaveAndLoad-> SaveAndLoad()
            }
        }
    }
}