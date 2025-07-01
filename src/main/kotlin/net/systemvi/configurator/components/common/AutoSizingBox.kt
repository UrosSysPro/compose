package net.systemvi.configurator.components.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class AutoSizingBoxItemPosition(val x:Dp, val y: Dp)

@Composable fun AutoSizingBox(content: @Composable () -> Unit) {
    Layout(content = content) { measurables, constraints ->
        val placeablesWithOffset = measurables.map { measurable ->
            val placeable = measurable.measure(constraints)

            val data=measurable.layoutId
            val (x:Dp,y: Dp) = when(data){
                is AutoSizingBoxItemPosition -> Pair(data.x,data.y)
                else -> Pair(0.dp,0.dp)
            }

            Triple(placeable, x.toPx().toInt(), y.toPx().toInt())
        }

        val width = placeablesWithOffset.maxOf { it.second + it.first.width }
        val height = placeablesWithOffset.maxOf { it.third + it.first.height }

        layout(width, height) {
            placeablesWithOffset.forEach { (placeable, x, y) ->
                placeable.place(x, y)
            }
        }

    }
}