package net.systemvi.configurator.components.design

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import net.systemvi.configurator.model.KeyMap

@OptIn(ExperimentalFoundationApi::class)
@Composable fun DesignPage(modifier: Modifier) {
    var keymap by remember { mutableStateOf(KeyMap("Untitled 1", listOf(listOf()))) }
    val viewModel = viewModel { DesignPageViewModel() }

    Column(
        modifier = modifier.then(
            Modifier
                .height(intrinsicSize = IntrinsicSize.Min)
                .padding(horizontal = 150.dp)
        )
    ) {
        AddRowButton(keymap, { keymap = it })
        keymap.keycaps.forEachIndexed { i, row ->
            val paddingBottom = 50 * row.fold(0f){acc, keycap -> acc.coerceAtLeast(keycap.padding.bottom)}

            Row(
                modifier = Modifier
                    .wrapContentSize(unbounded = true)
                    .padding(bottom = paddingBottom.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            )
            {
                AddKeycapButton(keymap, i, { keymap = it })
                row.forEachIndexed { j, key ->
                    val width = 50 * key.width.size
                    val height = 50 * key.height.size
                    val leftPadding = 50 * key.padding.left

                    Box(
                        modifier = Modifier
                            .padding(start = leftPadding.dp)
                            .size(width.dp, height.dp)
                            .wrapContentSize(unbounded = true),
                    ) {
                        KeycapDesign(keymap, i, j, { keymap = it }, { viewModel.showEdit = true; })
                        KeycapEdit(keymap, i, j, { keymap = it })
                    }
                }
                RemoveRowButton(keymap, i, { keymap = it })
            }
        }
    }
}
