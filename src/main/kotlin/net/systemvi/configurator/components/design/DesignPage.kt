package net.systemvi.configurator.components.design

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import net.systemvi.configurator.components.common.DraggableList
import net.systemvi.configurator.components.common.DraggableListDirection
import net.systemvi.configurator.model.KeyMap
import net.systemvi.configurator.model.KeycapPosition
import net.systemvi.configurator.model.Keycap
import net.systemvi.configurator.model.forEveryKeycapPositioned
import net.systemvi.configurator.utils.export.round_filet_design.KeycapPadding
import net.systemvi.configurator.utils.export.round_filet_design.KeycapSize

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
        AddRowAndSaveAsButton(rowLimit, oneUSize)
        DraggableList(
            items = keymap.keycaps,
            key = {it},
            onDrop = {}
        ){ i, row, isSelected ->
            KeymapRow(row, i, isSelected, keycapLimit, oneUSize)
        }
    }
    if(viewModel.selectedKeycap != null) {
        if(showFloatingActionButtons) KeycapEdit(keymap, viewModel.selectedKeycap!!, viewModel::updateKeymap)
    }
//    Column {
//        EditableGrid(keymap,onChange={})
//        Row{
//            Tabs(
//                KeycapSettings(),
//                keyboardSettings(),
//                KeyboardJson(),
//                Summary(),
//            )
//            when(selectedPage){
//                KeycapSettingsTab -> KeycapSettings()
//                KeyboardSettingsTab -> KeyboardSettings()
//                KeybaordJsonTab -> KeyboardSettings()
//                SummaryTab -> Summary()
//            }
//        }
//    }
}

@Composable
fun AddRowAndSaveAsButton(rowLimit: Int, oneUSize: Int){
    val viewModel = viewModel { DesignPageViewModel() }
    val keymap = viewModel.keymap

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        AddRowButton(viewModel::addRow, keymap.keycaps.size>=rowLimit, oneUSize)
        SaveAsButton(keymap, viewModel::setName, keymap.keycaps.flatten().isNotEmpty())
    }
}

@Composable
fun KeymapRow(row: List<Keycap>, i:Int, isSelected: Boolean, keycapLimit: Int, oneUSize: Int){
    val viewModel = viewModel { DesignPageViewModel() }
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
            OneKeycap(keycap, i, j, isSelected, oneUSize)
        }
        RemoveRowButton({ viewModel.removeRow(i) }, oneUSize)
    }

}

@Composable
fun OneKeycap(keycap: Keycap, i:Int, j: Int, isSelected: Boolean, oneUSize: Int){
    val viewModel = viewModel { DesignPageViewModel() }

    val width = oneUSize * keycap.width.size
    val height = oneUSize * keycap.height.size
    val leftPadding = oneUSize * keycap.padding.left

    Box(
        modifier = Modifier
            .graphicsLayer(
                alpha = if(isSelected)0f else 1f,
            )
            .padding(start = leftPadding.dp)
            .size(width.dp, oneUSize.dp)
            .wrapContentSize(unbounded = true, align = Alignment.TopCenter),
    ) {
        KeycapDesign(keycap, { viewModel.deleteKeycap(i, j) }, {
            viewModel.selectedKeycap = KeycapPosition(i, j)
        }, oneUSize)
    }
}

