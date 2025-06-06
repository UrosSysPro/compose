package net.systemvi.configurator.components.tester

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.collections.flatMap

private data class GridItem(val value:String,val width:Float,val height:Float)


@Composable fun Grid(items:List<String>) {
    val filteredItems = items.map {
        it.split(" ").map {
            val matches = "%(.*),([0-9]+.[0-9]+),([0-9]+.[0-9]+)".toRegex().find(it);
            if (matches == null) {
                GridItem(it, 1f, 1f)
            } else {
                val (name, width, height) = matches.destructured
                GridItem(name.replace("\\s"," "), width.toFloat(), height.toFloat())
            }
        }
    }
    val size = 40f
    var minSize = 1f
    Box(Modifier.size(500.dp, 500.dp)) {
        var currentX = 0f
        var currentY = 0f
        filteredItems.zip(filteredItems.indices).forEach { (row, j) ->
            row.zip(row.indices).forEach { (item, i) ->
                minSize = minSize.coerceAtMost(item.height)
                Box(
                    modifier = Modifier
                        .offset(currentX.dp, currentY.dp)
                        .border(
                            if (item.value.isNotEmpty()) BorderStroke(
                                2.dp,
                                MaterialTheme.colors.primary
                            ) else BorderStroke(
                                0.dp,
                                Color(0, 0, 0, 0)
                            ),
                        )
                        .size((size * item.width).dp, (size * item.height).dp)
                ) {
                    Text(item.value, modifier = Modifier.align(Alignment.Center))
                    currentX += size * item.width
                }
            }
            currentX = 0f
            currentY += minSize * size
            minSize = 1f
        }
    }
}
