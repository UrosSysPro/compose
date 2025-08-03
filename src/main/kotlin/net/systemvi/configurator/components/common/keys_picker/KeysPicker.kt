package net.systemvi.configurator.components.common.keys_picker

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import net.systemvi.configurator.components.configure.KeyboardKeysPages
import net.systemvi.configurator.model.Key
import net.systemvi.configurator.model.Macro
import net.systemvi.configurator.model.SnapTapPair
import net.systemvi.configurator.model.padding

@Composable
fun KeysPicker(
    categories: List<KeyboardKeysPages>,
    selectedCategory: KeyboardKeysPages,
    onCategorySelect:(KeyboardKeysPages)->Unit,
    onNormalKeySelected:(Key)->Unit = {},
    onMacroKeySelected:(Macro)->Unit = {},
    onLayerKeySelected:(layer:Int)->Unit = {},
    snapTapPairs:List<SnapTapPair> = emptyList(),
    macros:List<Macro> = emptyList(),
    onAddSnapTap:()->Unit = {},
    onRemoveSnapTap:(SnapTapPair)->Unit = {},
    onAddMacro:(Macro)->Unit = {},
    onRemoveMacro:(Macro)->Unit = {},
) {

    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ){
            categories.forEach { category ->
//                IconButton(
//                    onClick = { onCategorySelect(category) }
//                ){
//                    Icon(
//                        imageVector = Icons.Filled.KeyboardArrowUp,
//                        contentDescription = "keys category icon",
//                    )
//                }
                TextButton(
                    onClick = { onCategorySelect(category) },
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = if(category==selectedCategory) MaterialTheme.colorScheme.secondaryContainer else Color.Transparent,
                    )
                ){
                    Text(text = category.title)
                }
            }
        }
        KeyCategoryGrid(
            page = selectedCategory,
            macros = macros,
            onAddMacro = onAddMacro,
            onRemoveMacro = onRemoveMacro,
            onNormalKeySelected = onNormalKeySelected,
            onMacroKeySelected = onMacroKeySelected,
            onLayerKeySelected = onLayerKeySelected,
            snapTapPairs = snapTapPairs,
            onAddSnapTap = onAddSnapTap,
            onRemoveSnapTap = onRemoveSnapTap,
        )
    }
}