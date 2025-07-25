package net.systemvi.configurator.components.design

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import net.systemvi.configurator.components.common.DraggableList
import net.systemvi.configurator.components.common.DraggableListDirection
import net.systemvi.configurator.components.configure.KeycapPosition

@OptIn(ExperimentalFoundationApi::class)
@Composable fun DesignPage(modifier: Modifier, showFloatingActionButtons: Boolean = true, keycapLimit: Int = 20, rowLimit: Int = 10, oneUSize:Int = 50) {
    val viewModel = viewModel { DesignPageViewModel() }
    val keymap = viewModel.keymap

    Column(
        modifier = modifier.then(
            Modifier
                .height(intrinsicSize = IntrinsicSize.Min)
                .padding(horizontal = 150.dp)
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            AddRowButton(viewModel::addRow, keymap.keycaps.size>=rowLimit, oneUSize)
            SaveAsButton(keymap, viewModel::setName, keymap.keycaps.flatten().isNotEmpty())
        }
        DraggableList(
            items = keymap.keycaps,
            key = {it},
            onDrop = {}
        ){ i, row, isSelected ->
            val paddingBottom = oneUSize * row.fold(0f){acc, keycap -> acc.coerceAtLeast(keycap.padding.bottom)}

            Row(
                modifier = Modifier
                    .graphicsLayer(
                        alpha = if(isSelected)0f else 1f,
                    )
                    .wrapContentSize(unbounded = true)
                    .padding(bottom = (paddingBottom+10).dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            )
            {
                AddKeycapButton({ viewModel.addKeycap(i) }, row.size >= keycapLimit, oneUSize)
                DraggableList(
                    items = row,
                    key = {it},
                    onDrop = {},
                    direction = DraggableListDirection.horizontal
                ){ j,keycap, isSelected->
                    val width = oneUSize * keycap.width.size
                    val height = oneUSize * keycap.height.size
                    val leftPadding = oneUSize * keycap.padding.left

                    Box(
                        modifier = Modifier
                            .graphicsLayer(
                                alpha = if(isSelected)0f else 1f,
                            )
                            .padding(start = leftPadding.dp)
                            .size(width.dp, height.dp)
                            .wrapContentSize(unbounded = true),
                    ) {
                        KeycapDesign(keycap, { viewModel.deleteKeycap(i, j) }, {
                            viewModel.selectedKeycap = KeycapPosition(i, j)
                        }, oneUSize)
                    }
                }
                RemoveRowButton({ viewModel.removeRow(i) }, oneUSize)
            }
        }
    }
    if(viewModel.selectedKeycap != null) {
        if(showFloatingActionButtons) KeycapEdit(keymap, viewModel.selectedKeycap!!, viewModel::updateKeymap)
    }
}

@Composable
fun DesignHoverCard(){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(bottom=20.dp)
            .shadow(elevation = 20.dp,RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(20.dp))
            .padding(20.dp)
            .size(900.dp,400.dp)
    ){
        DesignPage(Modifier.fillMaxSize(), showFloatingActionButtons = false, keycapLimit = 8, rowLimit = 5)
    }
}

