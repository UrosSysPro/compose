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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import arrow.core.toOption
import net.systemvi.configurator.components.common.AutoSizingBox
import net.systemvi.configurator.data.allKeys

private data class GridItem(val value:String,val width:Float,val height:Float)

data class AutoSizingBoxItemPosition(val x: Dp, val y:Dp)

@Composable fun Grid(items:List<String>, keycap:@Composable (String, Boolean, Boolean)->Unit) {
    val viewModel = viewModel{ TesterPageViewModel() }

    val filteredItems = items.map {
        it.split(" ").map {
            val matches = "%(.*),([0-9]+.[0-9]+),([0-9]+.[0-9]+)".toRegex().find(it);
            if (matches == null) {
                GridItem(it, 1f, 1f)
            } else {
                val (name, width, height) = matches.destructured
                GridItem(name.replace("\\s", " "), width.toFloat(), height.toFloat())
            }
        }
    }
    val size = 50f
    var minSize = 1f
    val onKeyEvent:(KeyEvent) -> Boolean = {
        if(it.type!= KeyEventType.Unknown){
            val key = allKeys.find { key-> key.value.toInt() == it.key.nativeKeyCode.toInt() }.toOption()
            key.onSome { key->
                when (it.type){
                    KeyEventType.KeyDown -> {
                        viewModel.currentlyDownKeys += key
                        viewModel.wasDownKeys += key
                    }
                    KeyEventType.KeyUp -> {
                        viewModel.currentlyDownKeys -= key
                    }

                }
            }
//            println("""
//                |native code: ${it.key.nativeKeyCode}
//                |key code: ${it.key.keyCode}
//                |native location: ${it.key.nativeKeyLocation}
//            """.trimIndent())
            true
        }else false
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
                    minSize = minSize.coerceAtMost(item.height)
                    Box(
                        modifier = Modifier
                            .layoutId(AutoSizingBoxItemPosition(currentX.dp, currentY.dp))
                            .size((size * item.width).dp, (size * item.height).dp)
                    ) {
                        val key = allKeys.find{it.name.uppercase() == item.value.uppercase()}
                        keycap(item.value, viewModel.wasDownKeys.contains(key), viewModel.currentlyDownKeys.contains(key))
                        currentX += size * item.width
                    }
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
