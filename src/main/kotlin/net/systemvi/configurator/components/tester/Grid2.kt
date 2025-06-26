package net.systemvi.configurator.components.tester

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.nativeKeyCode
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import arrow.core.getOrElse
import arrow.core.toOption
import net.systemvi.configurator.components.common.AutoSizingBox
import net.systemvi.configurator.data.allKeys
import net.systemvi.configurator.data.alphabetKeys
import net.systemvi.configurator.model.Key
import net.systemvi.configurator.model.KeyMap
import net.systemvi.configurator.model.Keycap
import kotlin.collections.minus
import kotlin.collections.plus

@Composable fun Grid2(keymap: KeyMap,keycapComponent: @Composable (String, Boolean, Boolean)->Unit) {
    data class GridItem(val keycap: Keycap, val x:Int, val y:Int)

    val viewModel = viewModel{ TesterPageViewModel() }

    var currentlyDownCodes by remember { mutableStateOf(emptySet<Byte>()) }
    var wasDownCodes by remember { mutableStateOf(emptySet<Byte>()) }

    LaunchedEffect(viewModel.currentlyDownKeys){
       currentlyDownCodes = viewModel.currentlyDownKeys.map{ it.value }.toSet()
    }
    LaunchedEffect(viewModel.wasDownKeys){
        wasDownCodes = viewModel.wasDownKeys.map{ it.value }.toSet()
    }

    val filteredItems= keymap.keycaps.mapIndexed { j,row->
        row.mapIndexed { i,keycap->
            GridItem(keycap,i,j)
        }
    }
    val size = 50f
    var minSize = 1f

    val passKey=alphabetKeys.last()

    fun processEvent(type: KeyEventType,key: Key){
        when (type){
            KeyEventType.KeyDown -> {
                viewModel.currentlyDownKeys += key
                viewModel.wasDownKeys += key
                for(row in filteredItems){
                    for(item in row){
                        val currentKey=item.keycap.layers[0].getOrElse { passKey }
                        if(currentKey.value==key.value){
                            viewModel.channels?.get(0)?.noteOn(item.x + item.y * 12, 93)
                        }
                    }
                }
            }
            KeyEventType.KeyUp -> {
                viewModel.currentlyDownKeys -= key
                for(row in filteredItems){
                    for(item in row){
                        val currentKey=item.keycap.layers[0].getOrElse { passKey }
                        if(currentKey.value==key.value){
                            viewModel.channels?.get(0)?.noteOff(item.x + item.y * 12)
                        }
                    }
                }
            }

        }
    }

    val onKeyEvent:(KeyEvent) -> Boolean = { event->
        if(event.type == KeyEventType.Unknown){
            false
        }else{
            processEvent(
                event.type,
                allKeys.find { key-> key.nativeCode.toInt() == event.key.nativeKeyCode }.toOption().getOrElse {
                    println(event.key.nativeKeyCode)
                    passKey
                }
            )
            true
        }
    }

    Box(Modifier
        .onKeyEvent(onKeyEvent)
        .focusRequester(viewModel.focusRequester)
        .focusable()
    ) {
        AutoSizingBox {
            var currentX = 0f
            var currentY = 0f
            filteredItems.zip(filteredItems.indices).forEach { (row, j) ->
                row.zip(row.indices).forEach { (item, i) ->
                    val keycap=item.keycap
                    val width=keycap.width.size
                    val height=keycap.height.size
                    val key=keycap.layers[0].getOrElse { passKey }
                    minSize = minSize.coerceAtMost(height)

                    Box(
                        modifier = Modifier
                            .layoutId(AutoSizingBoxItemPosition(currentX.dp, currentY.dp))
                            .size((size * keycap.width.size).dp, (size * height).dp)
                    ) {
                        keycapComponent(key.name, wasDownCodes.contains(key.value), currentlyDownCodes.contains(key.value))
                    }
                    currentX += size * width
                }
                currentX = 0f
                currentY += minSize * size
                minSize = 1f
            }
        }
        LaunchedEffect(Unit) {
            viewModel.focusRequester.requestFocus()
        }
    }
}