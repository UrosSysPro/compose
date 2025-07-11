package net.systemvi.configurator.components.configure.keyboard_keys.macro

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import arrow.core.None
import arrow.core.Option
import net.systemvi.configurator.model.Macro
import net.systemvi.configurator.model.actions


@Composable fun Macros(){
    var macros by remember{mutableStateOf(emptyList<Macro>())}
    var isMacroEditorOpened by remember {mutableStateOf(false)}
    var currentlyEditedMacro by remember { mutableStateOf<Option<Macro>>(None) }

    LaunchedEffect(isMacroEditorOpened) {
        if(!isMacroEditorOpened)currentlyEditedMacro = None
    }

    Column {
        Text("Macros")
        if(isMacroEditorOpened){
            MacroEditor(
                initialMacro = currentlyEditedMacro,
                onSave = {
                    macros = macros.map { macro -> if(it.name==macro.name) it else macro }
                    isMacroEditorOpened = false
                },
                onSaveCopy={
                    macros = macros+it
                    isMacroEditorOpened=false
                },
                onCancel = {
                    isMacroEditorOpened=false
                })
        }else{
            MacroRow(
                macros=macros,
                onClick = {
                    //assign to key
                },
                onEdit = {
                    currentlyEditedMacro = it
                    isMacroEditorOpened = true
                }
            )
        }
    }
}