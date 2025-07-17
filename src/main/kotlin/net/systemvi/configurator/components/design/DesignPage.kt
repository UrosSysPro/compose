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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import net.systemvi.configurator.components.configure.KeycapPosition

@OptIn(ExperimentalFoundationApi::class)
@Composable fun DesignPage(modifier: Modifier, showFloatingActionButtons: Boolean = true, keycapLimit: Int = 20, rowLimit: Int = 10) {
    val viewModel = viewModel { DesignPageViewModel() }

    Column(
        modifier = modifier.then(
            Modifier
                .height(intrinsicSize = IntrinsicSize.Min)
                .padding(horizontal = 150.dp)
        )
    ) {
        AddRowButton(viewModel.keymap, { viewModel.keymap = it }, viewModel.keymap.keycaps.size >= rowLimit)
        viewModel.keymap.keycaps.forEachIndexed { i, row ->
            val paddingBottom = 50 * row.fold(0f){acc, keycap -> acc.coerceAtLeast(keycap.padding.bottom)}

            Row(
                modifier = Modifier
                    .wrapContentSize(unbounded = true)
                    .padding(bottom = (paddingBottom+10).dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            )
            {
                AddKeycapButton(viewModel.keymap, i, { viewModel.keymap = it }, viewModel.keymap.keycaps[i].size >= keycapLimit)
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
                        KeycapDesign(viewModel.keymap, i, j, { viewModel.keymap = it }, {
                            viewModel.selectedKeycap = KeycapPosition(i,j) })
                    }
                }
                RemoveRowButton(viewModel.keymap, i, { viewModel.keymap = it })
            }
        }
    }
    if(viewModel.selectedKeycap != null) {
        if(showFloatingActionButtons) KeycapEdit(viewModel.keymap, viewModel.selectedKeycap!!, { viewModel.keymap = it })
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

