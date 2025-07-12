package net.systemvi.configurator.components.configure.keyboard_keys
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import net.systemvi.configurator.components.common.BorderVertical
import net.systemvi.configurator.components.configure.ConfigureViewModel
import net.systemvi.configurator.components.configure.KeyboardKeysPages
import net.systemvi.configurator.components.configure.keyboard_keys.layers.LayerKeys
import net.systemvi.configurator.components.configure.keyboard_keys.macro.Macros
import net.systemvi.configurator.components.configure.keyboard_keys.snaptap.SnapTap

@Composable fun KeyboardKeys() {
    val viewModel= viewModel { ConfigureViewModel() }
    Row {
        SidePanel(viewModel.currentKeyboardKeysPage,{page -> viewModel.setKeyboardKeysPage(page)})
        BorderVertical()
        when(viewModel.currentKeyboardKeysPage) {
            KeyboardKeysPages.MacroKeys -> Macros()
            KeyboardKeysPages.LayerKeys -> LayerKeys()
            KeyboardKeysPages.SnapTapKeys -> SnapTap()
            else -> Keys(viewModel.currentKeyboardKeysPage.keys)
        }
    }
}