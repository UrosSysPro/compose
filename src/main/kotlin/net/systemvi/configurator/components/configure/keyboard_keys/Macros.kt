package net.systemvi.configurator.components.configure.keyboard_keys

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import arrow.core.None
import arrow.core.Option
import net.systemvi.configurator.model.Macro

@Composable fun MacroRow(macros:List<Macro>){
    FlowRow(){
        ElevatedButton(
            onClick = {}
        ){
            Text("create")
        }
        macros.forEach { macro ->
            ElevatedButton(
                onClick = {}
            ){
                Text(macro.name)
            }
        }
    }
}

@Composable fun MacroEditor(initialMacro: Option<Macro> = None,onSave:(Macro)->Unit){

}

@Composable fun Macros(){
    var macros by remember{mutableStateOf(emptyList<Macro>())}
    var isMacroEditorOpened by remember {mutableStateOf(false)}
    var currentlyEditedMacro by remember { mutableStateOf<Option<Macro>>(None) }
    Column {
        Text("Macros")
        if(isMacroEditorOpened){
            MacroEditor(currentlyEditedMacro,{})
        }else{
            MacroRow(macros)
        }
    }
}