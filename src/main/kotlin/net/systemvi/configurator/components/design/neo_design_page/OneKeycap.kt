package net.systemvi.configurator.components.design.neo_design_page

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.dp
import net.systemvi.configurator.components.common.keyboard_grid.KeycapComponent
import net.systemvi.configurator.components.common.keyboard_grid.KeycapParam
import net.systemvi.configurator.model.Keycap
import net.systemvi.configurator.model.KeycapPosition

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun OneKeycap(
    keycap: Keycap,
    i:Int, j: Int,
    isSelected: Boolean,
    keycapType: KeycapComponent,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    oneUSize: Int
) {

    val width = oneUSize * keycap.width.size
    val height = oneUSize * keycap.height.size
    val leftPadding = oneUSize * keycap.padding.left

    var isHovered by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .graphicsLayer(
                alpha = if (isSelected) 0f else 1f,
            )
            .padding(start = leftPadding.dp)
            .size(width.dp, oneUSize.dp)
            .wrapContentSize(unbounded = true, align = Alignment.TopCenter),
    ) {
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
                    onClick = onClick,
                )
        ) {
            keycapType(
                KeycapParam(keycap, KeycapPosition(i, j))
            )
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
}