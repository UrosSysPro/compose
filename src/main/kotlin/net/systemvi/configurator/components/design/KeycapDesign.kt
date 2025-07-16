package net.systemvi.configurator.components.design

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.PointerMatcher
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.PointerButton
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.dp
import arrow.core.right
import arrow.optics.dsl.index
import net.systemvi.configurator.components.common.keyboard_grid.KeycapParam
import net.systemvi.configurator.components.common.keycaps.FlatKeycap
import net.systemvi.configurator.components.configure.KeycapPosition
import net.systemvi.configurator.components.tester.keycaps.FlatKeycap
import net.systemvi.configurator.data.allKeys
import net.systemvi.configurator.model.KeyMap
import net.systemvi.configurator.model.Keycap
import net.systemvi.configurator.model.keycaps

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun KeycapDesign(keymap: KeyMap, row: Int, key: Int, onDelete: (keymap: KeyMap) -> Unit, onRightClick: () -> Unit){
    var isHovered by remember { mutableStateOf(false) }
    val keycap = keymap.keycaps[row][key]
    val width = 50 * keycap.width.size
    val height = 50 * keycap.height.size

    Box(
        modifier = Modifier
            .size(width = width.dp, height = height.dp)
            .onPointerEvent(PointerEventType.Enter) {
                isHovered = true
            }
            .onPointerEvent(PointerEventType.Exit) {
                isHovered = false
            }
            .onClick(
                enabled = true,
                matcher = PointerMatcher.mouse(PointerButton.Secondary),
                onClick = onRightClick
            )
    ) {
        FlatKeycap(
            KeycapParam(
                Keycap(listOf(allKeys[0].right())),
                KeycapPosition(0, 0)
            )
        )
        if (isHovered) {
            IconButton(
                onClick = {
                    onDelete(KeyMap.keycaps.index(row).modify(keymap, {
                        it.filterIndexed { index, _ -> index != key }
                    }))
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .clip(CircleShape)
                    .size(20.dp)
                    .background(
                        color = MaterialTheme.colorScheme.error,
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Remove",
                    tint = MaterialTheme.colorScheme.onError,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(10.dp)
                )
            }
        }
    }
}