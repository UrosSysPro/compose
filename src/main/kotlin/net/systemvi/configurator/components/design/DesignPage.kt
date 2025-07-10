package net.systemvi.configurator.components.design

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.dp
import arrow.core.right
import arrow.optics.dsl.index
import net.systemvi.configurator.components.common.keyboard_grid.KeycapParam
import net.systemvi.configurator.components.common.keycaps.FlatKeycap
import net.systemvi.configurator.components.configure.KeycapPosition
import net.systemvi.configurator.data.allKeys
import net.systemvi.configurator.model.KeyMap
import net.systemvi.configurator.model.Keycap
import net.systemvi.configurator.model.keycaps

@OptIn(ExperimentalComposeUiApi::class)
@Composable fun DesignPage(modifier: Modifier) {
    var keymap by remember { mutableStateOf(KeyMap("Untitled 1", listOf(listOf()))) }

    Column(
        modifier = modifier.then(
            Modifier
                .height(intrinsicSize = IntrinsicSize.Min)
                .padding(horizontal = 150.dp)
        )
    ) {
        ElevatedButton(
            onClick = {
                keymap = KeyMap.keycaps.modify(keymap, {
                    it + listOf(listOf())
                })
            },
        ) {
            Text(
                "Add Row",
                style = MaterialTheme.typography.bodySmall
            )
        }
        keymap.keycaps.forEachIndexed { i, row ->
            Row(
                modifier = Modifier
                    .height(70.dp)
                    .padding(bottom = 10.dp)
                    .border(
                        BorderStroke(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            )
            {
                ElevatedButton(
                    onClick = {
                        keymap = KeyMap.keycaps.index(i).modify(keymap, {
                            it + Keycap(
                                listOf(allKeys[0].right())
                            )
                        })
                    },
                    modifier = Modifier
                        .padding(end = 10.dp)
                ) {
                    Text(
                        "Add Keycap",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                row.forEachIndexed { j, key ->
                    var isHovered by remember { mutableStateOf(false) }

                    Box(
                        modifier = Modifier
                            .size(50.dp, 50.dp)
                            .onPointerEvent(PointerEventType.Enter) {
                                isHovered = true
                            }
                            .onPointerEvent(PointerEventType.Exit) {
                                isHovered = false
                            }
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
                                    keymap = KeyMap.keycaps.index(i).modify(keymap, {
                                        it.filterIndexed { index,_->index!=j }
                                    })
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
                ElevatedButton(
                    onClick = {
                        keymap = KeyMap.keycaps.modify(keymap, {keycaps ->
                            keycaps.filterIndexed {index, _ -> index != i}
                        })
                    },
                    modifier = Modifier
                        .padding(start = 10.dp)
                ) {
                    Text(
                        "Remove Row",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}
