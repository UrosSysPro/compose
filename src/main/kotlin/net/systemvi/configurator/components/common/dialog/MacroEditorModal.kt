package net.systemvi.configurator.components.common.dialog

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import arrow.core.toOption
import net.systemvi.configurator.data.allKeys
import net.systemvi.configurator.data.alphabetBigKeys
import net.systemvi.configurator.data.alphabetKeys
import net.systemvi.configurator.model.*


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
@Composable
fun MacroEditorModal(
    show: Boolean,
    initialMacro: Macro,
    onSave:(Macro)->Unit,
    onCancel:()->Unit
){
    val gap = 20.dp

    var macro by remember { mutableStateOf(initialMacro) }

    LaunchedEffect(initialMacro){
        macro=initialMacro
    }

    LaunchedEffect(show){
        macro = initialMacro
    }

    if(show) Dialog(onDismissRequest = { onCancel() }) {
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

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(gap))
                .padding(gap),
            verticalArrangement = Arrangement.spacedBy(gap),
            horizontalAlignment = Alignment.Start
        ) {
            OutlinedTextField(
                value = macro.name,
                label = { Text("Name") },
                onValueChange = { macro = macro.setName(it) },
            )

            Row(Modifier
                .focusRequester(focusRequester)
                .focusable(interactionSource = interactionSource)
                .fillMaxWidth()
                .height(200.dp)
                .onClick(onClick = { focusRequester.requestFocus() })
                .onKeyEvent(onKeyEvent)
                .shadow(elevation,shape = RoundedCornerShape(gap))
                .background(boxBackground, shape = RoundedCornerShape(gap)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            ) {
//                DraggableList(macro.actions,{macro.actions.indexOf(it)},{}, DraggableListDirection.horizontal){ index,action,isDragged ->
//                    if(!isDragged)MacroActionItem(action)
//                }
                macro.actions.forEach { MacroActionItem(it) }
            }

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(gap, Alignment.End),
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
}