package net.systemvi.configurator.components.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp

@Composable fun AutoSizingBox(content: @Composable () -> Unit) {
    Layout(content = content) { measurables, constraints ->
        val placeablesWithOffset = measurables.map { measurable ->
            val placeable = measurable.measure(constraints)
            val layoutData:Pair<Int,Int>? = measurable.layoutId as Pair<Int, Int>?
            val (x,y) = (layoutData ?: Pair(0, 0))

            val xOffset = x.dp.toPx().toInt()
            val yOffset = y.dp.toPx().toInt()

            Triple(placeable, xOffset, yOffset)
        }

        val width = placeablesWithOffset.maxOf { it.second + it.first.width }
        val height = placeablesWithOffset.maxOf { it.third + it.first.width }

        layout(width, height) {
            placeablesWithOffset.forEach { (placeable, x, y) ->
                placeable.place(x, y)
            }
        }

    }
}