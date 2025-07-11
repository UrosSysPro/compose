package net.systemvi.configurator.components.configure.keyboard_keys.macro

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import arrow.core.None
import arrow.core.Option
import net.systemvi.configurator.components.configure.ConfigureViewModel
import net.systemvi.configurator.model.Macro


@Composable fun Macros(){
    var isMacroEditorOpened by remember {mutableStateOf(false)}
    var currentlyEditedMacro by remember { mutableStateOf<Option<Macro>>(None) }
    var viewModel= viewModel { ConfigureViewModel() }
    var macros by remember{mutableStateOf(viewModel.keymapApi.macroKeys())}

    LaunchedEffect(isMacroEditorOpened) {
        if(!isMacroEditorOpened)currentlyEditedMacro = None
    }

    Column {
        Text("Macros")
        if(isMacroEditorOpened){
            MacroEditor(
                initialMacro = currentlyEditedMacro,
                onSave = { newMacro->
                    currentlyEditedMacro
                        .onSome { edited -> macros = macros.map { macro -> if(edited == macro) newMacro else macro } }
                        .onNone { macros = macros + newMacro }
                    isMacroEditorOpened = false
                },
                onCancel = {
                    isMacroEditorOpened = false
                })
        }else{
            MacroRow(
                macros=macros,
                onClick = {
                    viewModel.setMacroKey(it)
                },
                onEdit = {
                    currentlyEditedMacro = it
                    isMacroEditorOpened = true
                }
            )
        }
    }
}