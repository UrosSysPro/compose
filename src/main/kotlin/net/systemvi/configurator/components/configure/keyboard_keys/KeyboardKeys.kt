package net.systemvi.configurator.components.configure.keyboard_keys
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import net.systemvi.configurator.components.common.BorderVertical

@Composable fun KeyboardKeys() {
    Row {
        SidePanel()
        BorderVertical()
        Keys()
    }
}