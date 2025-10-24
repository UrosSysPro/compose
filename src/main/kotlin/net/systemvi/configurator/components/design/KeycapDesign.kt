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
import net.systemvi.configurator.components.common.keyboard_grid.KeycapComponent
import net.systemvi.configurator.components.common.keycaps.FlatKeycap
import net.systemvi.configurator.model.KeyMap
import net.systemvi.configurator.model.Keycap

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun KeycapDesign(keycap: Keycap, onDelete: () -> Unit, onRightClick: () -> Unit, size: Int, keycapType: KeycapComponent){

    var isHovered by remember { mutableStateOf(false) }
    val width = size * keycap.width.size
    val height = size * keycap.height.size

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
                matcher = PointerMatcher.mouse(PointerButton.Primary),
                onClick = onRightClick
            )
    ) {
//        FlatKeycap(isDown = false, wasDown = false, "A")
        keycapType
        if (isHovered) {
            IconButton(
                onClick = onDelete,
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