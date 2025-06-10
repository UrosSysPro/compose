package net.systemvi.configurator.components.configure.keyboard_keys
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import net.systemvi.configurator.components.common.BorderVertical
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
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

enum class CurrentPage(val title:String,val keys:List<Key>){
    All("All", allKeys),
    Alphabet("Alphabet", alphabetKeys),
    Numbers("Numbers", numberKeys),
    Symbols("Symbols", symbolKeys),
    FKeys("Function Keys", fKeys),
    NumpadKeys("Numpad Keys", numpadKeys),
    ModifierKeys("Modifier Keys", modifierKeys),
    MiscKeys("Misc", miscKeys),
    MediaKeys("Media Keys", mediaKeys),
}
@Composable fun KeyboardKeys() {
    var currentPage: CurrentPage by remember { mutableStateOf(CurrentPage.All) }
    Row {
        SidePanel(currentPage,{page -> currentPage=page})
        BorderVertical()
        Keys(currentPage.keys)
    }
}