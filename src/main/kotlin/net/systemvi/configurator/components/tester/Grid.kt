package net.systemvi.configurator.components.tester

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import net.systemvi.configurator.components.common.AutoSizingBox

private data class GridItem(val value:String,val width:Float,val height:Float)


@Composable fun Grid(items:List<String>, keycap:@Composable (String, Boolean)->Unit) {
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
    val size = 40f
    var minSize = 1f
    AutoSizingBox {
        var currentX = 0f
        var currentY = 0f
        filteredItems.zip(filteredItems.indices).forEach { (row, j) ->
            row.zip(row.indices).forEach { (item, i) ->
                minSize = minSize.coerceAtMost(item.height)
                Box(
                    modifier = Modifier
//                        .offset(currentX.dp, currentY.dp)
                        .layoutId(Pair(currentX, currentY))
                        .size((size * item.width).dp, (size * item.height).dp)
                ) {
                    keycap(item.value, true)
                    currentX += size * item.width
                }
            }
            currentX = 0f
            currentY += minSize * size
            minSize = 1f
        }
    }
}
