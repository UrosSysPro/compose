package net.systemvi.configurator.components.design

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import net.systemvi.configurator.components.configure.KeycapPosition
import net.systemvi.configurator.model.KeyMap

@Composable fun DesignPage(modifier: Modifier) {
    var keymap by remember { mutableStateOf(KeyMap("Untitled 1", listOf(listOf()))) }
    var selectedKeycap by remember {  mutableStateOf(KeycapPosition(-1, -1)) }

    Column(
        modifier = modifier.then(
            Modifier
                .height(intrinsicSize = IntrinsicSize.Min)
                .padding(horizontal = 150.dp)
                .zIndex(1f)
        )
    ) {
        AddRowButton(keymap, { keymap = it })
        keymap.keycaps.forEachIndexed { i, row ->
            val paddingBottom = 50 * row.fold(0f){acc, keycap -> acc.coerceAtLeast(keycap.padding.bottom)}

            Row(
                modifier = Modifier
                    .zIndex(if (selectedKeycap.x == i) 10f else 1f)
                    .wrapContentSize(unbounded = true)
                    .padding(bottom = paddingBottom.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            )
            {
                AddKeycapButton(keymap, i, { keymap = it })
                row.forEachIndexed { j, key ->
                    var showEdit = selectedKeycap.x == i && selectedKeycap.y == j
                    val width = 50 * key.width.size
                    val height = 50 * key.height.size
                    val leftPadding = 50 * key.padding.left

                    Box(
                        modifier = Modifier
                            .zIndex(if (showEdit) 100f else 1f)
                            .padding(start = leftPadding.dp)
                            .size(width.dp, height.dp)
                            .wrapContentSize(unbounded = true)
                    ) {
                        KeycapDesign(keymap, i, j, { keymap = it }, { selectedKeycap = KeycapPosition(i, j) })
                        KeycapEdit(showEdit, keymap, i, j, {keymap = it; selectedKeycap = KeycapPosition(-1, -1)})
                    }
                }
                RemoveRowButton(keymap, i, { keymap = it })
            }
        }
    }
}
