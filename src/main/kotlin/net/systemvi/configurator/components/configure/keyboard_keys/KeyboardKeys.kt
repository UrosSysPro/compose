package net.systemvi.configurator.components.configure.keyboard_keys
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import net.systemvi.configurator.components.common.BorderVertical
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import net.systemvi.configurator.components.configure.ConfigureViewModel
import net.systemvi.configurator.components.configure.KeyboardKeysPages
import net.systemvi.configurator.data.allKeys
import net.systemvi.configurator.data.alphabetKeys
import net.systemvi.configurator.data.fKeys
import net.systemvi.configurator.data.mediaKeys
import net.systemvi.configurator.data.miscKeys
import net.systemvi.configurator.data.modifierKeys
import net.systemvi.configurator.data.numberKeys
import net.systemvi.configurator.data.numpadKeys
import net.systemvi.configurator.data.symbolKeys
import net.systemvi.configurator.model.Key

@Composable fun KeyboardKeys() {
    val viewModel= viewModel { ConfigureViewModel() }
    Row {
        SidePanel(viewModel.currentKeyboardKeysPage,{page -> viewModel.setKeyboardKeysPage(page)})
        BorderVertical()
        Keys(viewModel.currentKeyboardKeysPage.keys)
    }
}