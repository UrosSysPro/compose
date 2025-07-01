package net.systemvi.configurator.components.tester

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.*
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

@Composable fun Grid2(keymap: KeyMap,keycapComponent: @Composable (String, Boolean, Boolean)->Unit, oneUSize:Int=50) {
    data class GridItem(val keycap: Keycap, val x:Int, val y:Int)

    val viewModel = viewModel{ TesterPageViewModel() }

    val filteredItems= keymap.keycaps.mapIndexed { j,row->
        row.mapIndexed { i,keycap->
            GridItem(keycap,i,j)
        }
    }
    var minSize = 1f
    var maxPadding = 0f

    val passKey=alphabetKeys.last()

    fun processEvent(type: KeyEventType,key: Key){
        println(key)
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
                    val paddingLeft = keycap.padding.left
                    val paddingBottom = keycap.padding.bottom
                    val key=keycap.layers[0].getOrElse { passKey }
                    minSize = minSize.coerceAtMost(height)
                    maxPadding = maxPadding.coerceAtLeast(paddingBottom)
                    currentX += oneUSize * paddingLeft

                    Box(
                        modifier = Modifier
                            .layoutId(AutoSizingBoxItemPosition(currentX.dp, currentY.dp))
                            .size((oneUSize * width).dp, (oneUSize * height).dp)
                    ) {
                        keycapComponent(key.name, viewModel.wasDownKeys.contains(key), viewModel.currentlyDownKeys.contains(key))
                    }
                    currentX += oneUSize * width
                }
                currentX = 0f
                currentY += (minSize + maxPadding) * oneUSize
                minSize = 1f
                maxPadding = 0f
            }
        }
        LaunchedEffect(Unit) {
            viewModel.focusRequester.requestFocus()
        }
    }
}