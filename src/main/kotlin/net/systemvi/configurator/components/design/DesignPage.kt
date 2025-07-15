package net.systemvi.configurator.components.design

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import net.systemvi.configurator.components.configure.KeycapPosition

@OptIn(ExperimentalFoundationApi::class)
@Composable fun DesignPage(modifier: Modifier) {
    val viewModel = viewModel { DesignPageViewModel() }
    val keymap = viewModel.keymap

    Column(
        modifier = modifier.then(
            Modifier
                .height(intrinsicSize = IntrinsicSize.Min)
                .padding(horizontal = 150.dp)
        )
    ) {
        Row(){
            AddRowButton(viewModel::addRow)
            SaveAsButton(keymap, viewModel::setName)
        }
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
                AddKeycapButton({viewModel.addKeycap(i)})
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
                        KeycapDesign(keymap, i, j, {viewModel.deleteKeycap(i, j)}, {
                            viewModel.selectedKeycap = KeycapPosition(i,j) })
                    }
                }
                RemoveRowButton({viewModel.removeRow(i)})
            }
        }
    }
    if(viewModel.selectedKeycap != null) {
        KeycapEdit(keymap, viewModel.selectedKeycap!!, viewModel::updateKeymap)
    }
}
