package net.systemvi.configurator.components.configure.keyboard_keys.macro

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.nativeKeyCode
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import arrow.core.None
import arrow.core.Option
import arrow.core.getOrElse
import arrow.core.toOption
import net.systemvi.configurator.data.allKeys
import net.systemvi.configurator.data.alphabetBigKeys
import net.systemvi.configurator.data.alphabetKeys
import net.systemvi.configurator.model.Macro
import net.systemvi.configurator.model.MacroAction
import net.systemvi.configurator.model.MacroActionType
import net.systemvi.configurator.model.actions
import net.systemvi.configurator.model.setName

@Composable fun MacroActionItem(action: MacroAction){
    Column(
        Modifier
            .padding(end = 20.dp)
            .size(50.dp,100.dp)
            .background(Color.White, RoundedCornerShape(10.dp))
        ,
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            action.key.name,
            modifier = Modifier.weight(1f).wrapContentSize(Alignment.Center),
            textAlign = TextAlign.Center,
        )
        Divider()
        Icon(
            when (action.action) {
                MacroActionType.KEY_DOWN -> Icons.Filled.KeyboardArrowDown
                MacroActionType.KEY_UP -> Icons.Filled.KeyboardArrowUp
            },
            modifier = Modifier.weight(1f),
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = "macro action type"
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable fun MacroEditor(initialMacro: Option<Macro> = None, onSave:(Macro)->Unit, onCancel:()->Unit){
    fun emptyMacro(): Macro= Macro("", emptyList())
    var macro by remember { mutableStateOf( emptyMacro() ) }
    val interactionSource = remember { MutableInteractionSource() }
    val focused by interactionSource.collectIsFocusedAsState()
    val focusRequester = remember { FocusRequester() }

    val boxBackground by animateColorAsState(
        targetValue = if (focused)
            MaterialTheme.colorScheme.primaryContainer
        else
            MaterialTheme.colorScheme.secondaryContainer,
    )

    val elevation by animateDpAsState(
        targetValue = if (focused) 8.dp else 0.dp,
    )

    LaunchedEffect(initialMacro){
        macro = initialMacro.getOrElse { emptyMacro() }
    }

    val onKeyEvent:(KeyEvent)->Boolean={
        if(it.type== KeyEventType.Unknown){
            false
        }else{
            allKeys.find { key-> key.nativeCode.toInt() == it.key.nativeKeyCode }.toOption().onSome { key->
                val key=if(alphabetBigKeys.indexOf(key)!=-1){
                    alphabetKeys[alphabetBigKeys.indexOf(key)]
                }else{
                    key
                }
                println("Key ${key.name} ${key.value}")
                val eventType=if(it.type== KeyEventType.KeyUp) MacroActionType.KEY_UP else MacroActionType.KEY_DOWN
                macro=Macro.actions.modify(macro,{it+ MacroAction(key,eventType)})
            }
            true
        }
    }

    Column {
        OutlinedTextField(
            value = macro.name,
            label = { Text("Name") },
            onValueChange = { macro = macro.setName(it) },
        )
        Box(Modifier.weight(1f).padding(30.dp)) {
            Row(Modifier
                .focusRequester(focusRequester)
                .focusable(interactionSource = interactionSource)
                .fillMaxSize()
                .onClick(onClick = { focusRequester.requestFocus() })
                .onKeyEvent(onKeyEvent)
                .shadow(elevation,shape = RoundedCornerShape(10.dp))
                .background(boxBackground, shape = RoundedCornerShape(10.dp))
                .padding(horizontal = 30.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            ) {
                macro.actions.forEach {
                    MacroActionItem(it)
                }
            }
        }
        Row(
            Modifier.fillMaxWidth().padding(8.dp),
            horizontalArrangement = Arrangement.End,
        ) {
            OutlinedButton(
                onClick = { onSave(macro) },
            ){
                Text("Save")
            }
            OutlinedButton(
                onClick = { onCancel() },
            ){
                Text("Cancel")
            }
        }
    }
}
