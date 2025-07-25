package net.systemvi.configurator.components.configure.keyboard_layout

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import net.systemvi.configurator.components.common.BorderHorizontal
import net.systemvi.configurator.components.common.BorderVertical
import net.systemvi.configurator.components.common.keycaps.FlatKeycap
import net.systemvi.configurator.components.configure.ConfigureViewModel
import net.systemvi.configurator.components.configure.KeyboardLayoutPages
import net.systemvi.configurator.components.common.keyboard_grid.Grid


@Composable
fun KeyboardLayoutView() {
    val viewModel= viewModel { ConfigureViewModel() }
    Column {
        BorderHorizontal()
        Row{
            SidePanel(viewModel.currentKeyboardLayoutPage) { page -> viewModel.setKeyboardLayoutPage(page) }
            BorderVertical()
            when(viewModel.currentKeyboardLayoutPage){
                KeyboardLayoutPages.Keymap->Column{
                    LayerSelector()
                    BorderHorizontal()
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier= Modifier.fillMaxWidth().weight(1f)
                            .horizontalScroll(rememberScrollState())
                            .verticalScroll(rememberScrollState())
                    ) {
                        viewModel.keymap.onSome { keymap ->
                            Grid(keymap, ConfiguratorKeycapComponent,70)
                        }
                    }
                }
                KeyboardLayoutPages.SaveAndLoad-> SaveAndLoad()
            }
        }
    }
}