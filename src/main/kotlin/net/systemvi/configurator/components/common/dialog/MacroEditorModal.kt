package net.systemvi.configurator.components.common.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import net.systemvi.configurator.model.Macro

@Composable
fun MacroEditorModal(
    show: Boolean,
    macro: Macro,
    onSave:(Macro)->Unit,
    onCancel:()->Unit
){
    if(show) Dialog(onDismissRequest = { onCancel() }) {

    }
}