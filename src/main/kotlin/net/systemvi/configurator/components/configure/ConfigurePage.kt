package net.systemvi.configurator.components.configure

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import net.systemvi.configurator.components.common.BorderHorizontal
import net.systemvi.configurator.components.configure.keyboard_keys.KeyboardKeys
import net.systemvi.configurator.components.configure.keyboard_layout.KeyboardLayoutView

@Composable fun ConfigurePage(
    modifier: Modifier,
    configureViewModel: ConfigureViewModel= viewModel { ConfigureViewModel() }
){
    Column(modifier) {
        Box(Modifier.weight(1f)){KeyboardLayoutView()}
        BorderHorizontal()
        Box(Modifier.weight(1f)){KeyboardKeys()}
    }
}