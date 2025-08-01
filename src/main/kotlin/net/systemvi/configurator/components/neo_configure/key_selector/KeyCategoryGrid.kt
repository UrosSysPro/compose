package net.systemvi.configurator.components.neo_configure.key_selector

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.systemvi.configurator.components.configure.KeyboardKeysPages
import net.systemvi.configurator.model.Key
import net.systemvi.configurator.model.Macro
import net.systemvi.configurator.model.SnapTapPair

@Composable
fun KeyCategoryGrid(
    page: KeyboardKeysPages,
    onNormalKeySelected:(Key)->Unit={},
    onMacroKeySelected:(Macro)->Unit={},
    onLayerKeySelected:(layer:Int)->Unit={},
    snapTapPairs:List<SnapTapPair> = emptyList(),
    macros:List<Macro> = emptyList(),
    onAddSnapTap:()->Unit={},
    onRemoveSnapTap:(SnapTapPair)->Unit={},
    onAddMacro:(Macro)->Unit={},
    onRemoveMacro:(Macro)->Unit={},
){
    FlowRow(
        modifier = Modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ){
        when (page) {
            KeyboardKeysPages.LayerKeys -> {
                for(i in 1 until 4){
                    ElevatedButton(
                        onClick = {onLayerKeySelected(i)}
                    ){
                        Text("L${i+1}")
                    }
                }
            }
            KeyboardKeysPages.MacroKeys -> KeyCategoryMacro(macros,onAddMacro,onRemoveMacro,onMacroKeySelected)
            KeyboardKeysPages.SnapTapKeys -> KeyCategorySnapTap(snapTapPairs,onAddSnapTap,onRemoveSnapTap)
            else -> page.keys.forEach { key ->
                ElevatedButton(
                    onClick = {onNormalKeySelected(key)},
                ){
                    Text(key.name)
                }
            }
        }
    }
}