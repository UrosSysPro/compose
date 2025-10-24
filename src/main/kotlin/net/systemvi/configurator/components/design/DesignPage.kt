package net.systemvi.configurator.components.design

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import net.systemvi.configurator.components.design.neo_design_page.EditableGrid
import net.systemvi.configurator.components.design.neo_design_page.FlatKeycap
import net.systemvi.configurator.components.design.neo_design_page.tabs.DesignPageTabs
import net.systemvi.configurator.components.design.neo_design_page.tabs.KeyboardJson
import net.systemvi.configurator.components.design.neo_design_page.tabs.KeycapSettings
import net.systemvi.configurator.components.design.neo_design_page.tabs.KeymapSettings
import net.systemvi.configurator.components.design.neo_design_page.tabs.Summary
import net.systemvi.configurator.components.design.neo_design_page.tabs.Tabs

@OptIn(ExperimentalFoundationApi::class)
@Composable fun DesignPage(modifier: Modifier, showFloatingActionButtons: Boolean = true, keycapLimit: Int = 20, rowLimit: Int = 10, oneUSize:Int = 50) {
    val viewModel = viewModel { DesignPageViewModel() }
    val currentTab = viewModel.currentTab
    val keymap = viewModel.keymap

//    Column(
//        modifier = modifier.then(
//            Modifier
//                .height(intrinsicSize = IntrinsicSize.Min)
//                .padding(horizontal = 150.dp)
//        )
//    ) {
//        AddRowAndSaveAsButton(rowLimit, oneUSize)
//        DraggableList(
//            items = keymap.keycaps,
//            key = {it},
//            onDrop = {}
//        ){ i, row, isSelected ->
//            KeymapRow(row, i, isSelected, keycapLimit, oneUSize)
//        }
//    }
//    if(viewModel.selectedKeycap != null) {
//        if(showFloatingActionButtons) KeycapEdit(keymap, viewModel.selectedKeycap!!, viewModel::updateKeymap)
//    }

    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 100.dp, vertical = 100.dp)
    ) {
        EditableGrid(
            keymap,
            onChange = { viewModel.updateKeymap(it) },
            FlatKeycap,
            onSelection = { viewModel.selectedKeycaps = it })
        Tabs()
        when (currentTab) {
            DesignPageTabs.KeycapSettings -> KeycapSettings()
            DesignPageTabs.KeymapSettings -> KeymapSettings()
            DesignPageTabs.KeyboardJson -> KeyboardJson()
            DesignPageTabs.Summary -> Summary()
        }
    }
}

//@Composable
//fun AddRowAndSaveAsButton(rowLimit: Int, oneUSize: Int){
//    val viewModel = viewModel { DesignPageViewModel() }
//    val keymap = viewModel.keymap
//
//    Row(
//        horizontalArrangement = Arrangement.Center,
//        verticalAlignment = Alignment.CenterVertically
//    ){
//        AddRowButton(viewModel::addRow, keymap.keycaps.size >= rowLimit, oneUSize)
//        SaveAsButton(keymap, viewModel::setName, keymap.keycaps.flatten().isNotEmpty())
//    }
//}





