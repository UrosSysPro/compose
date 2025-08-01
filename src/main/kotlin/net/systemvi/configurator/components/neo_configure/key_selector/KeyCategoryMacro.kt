package net.systemvi.configurator.components.neo_configure.key_selector

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import net.systemvi.configurator.components.common.dialog.MacroEditorModal
import net.systemvi.configurator.data.emptyMacro
import net.systemvi.configurator.model.Macro

@Composable
fun KeyCategoryMacro(
    macroKeys: List<Macro>,
    onAddMacro:(Macro)->Unit,
    onRemoveMacro:(Macro)->Unit,
    onMacroKeySelected:(Macro)->Unit,
) {
    var showEditor by remember { mutableStateOf(false) }
    var editedMacro by remember { mutableStateOf(emptyMacro) }

    ElevatedButton(
        onClick = { showEditor = true }
    ){
        Text(text = "+")
    }

    macroKeys.forEach { macro->
        ElevatedButton(
            onClick = { onMacroKeySelected(macro) },
        ){
            Row (
                verticalAlignment = Alignment.CenterVertically
            ){
                var showMenu by remember { mutableStateOf(false) }
                Text(macro.name)
                IconButton(onClick = { showMenu=true }) {
                    Icon(Icons.Filled.MoreVert, contentDescription = "three dots")
                }
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false },
                ){
                    DropdownMenuItem(
                        text = { Text("Edit") },
                        onClick = { editedMacro = macro; showEditor = true; showMenu = false }
                    )
                    DropdownMenuItem(
                        text = { Text("Delete") },
                        onClick = { onRemoveMacro(macro); showMenu = false }
                    )
                }
            }
        }
    }

    MacroEditorModal(
        showEditor,
        editedMacro,
        onSave = { onAddMacro(it); showEditor = false },
        onCancel = { showEditor = false },
    )
}