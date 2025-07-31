package net.systemvi.configurator.components.common.keyboard_grid

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import net.systemvi.configurator.components.common.AutoSizingBox
import net.systemvi.configurator.components.common.AutoSizingBoxItemPosition
import net.systemvi.configurator.model.KeyMap
import net.systemvi.configurator.model.Keycap
import net.systemvi.configurator.model.KeycapPosition

@Composable fun Grid(keymap: KeyMap, keycapComponent: KeycapComponent, oneUSize:Int=50) {
    data class GridItem(val keycap: Keycap, val x: Int, val y: Int)

    val filteredItems = keymap.keycaps.mapIndexed { j, row ->
        row.mapIndexed { i, keycap ->
            GridItem(keycap, i, j)
        }
    }
    var minSize = 1f
    var maxPadding = 0f

    AutoSizingBox {
        var currentX = 0f
        var currentY = 0f
        filteredItems.zip(filteredItems.indices).forEach { (row, j) ->
            row.zip(row.indices).forEach { (item, i) ->
                val keycap = item.keycap
                val width = keycap.width.size
                val height = keycap.height.size
                val paddingLeft = keycap.padding.left
                val paddingBottom = keycap.padding.bottom
                minSize = minSize.coerceAtMost(height)
                maxPadding = maxPadding.coerceAtLeast(paddingBottom)
                currentX += oneUSize * paddingLeft

                Box(
                    modifier = Modifier
                        .layoutId(AutoSizingBoxItemPosition(currentX.dp, currentY.dp))
                        .size((oneUSize * width).dp, (oneUSize * height).dp)
                ) {
                    keycapComponent(
                        KeycapParam(item.keycap, KeycapPosition(i,j))
                    )
                }
                currentX += oneUSize * width
            }
            currentX = 0f
            currentY += (minSize + maxPadding) * oneUSize
            minSize = 1f
            maxPadding = 0f
        }
    }
}