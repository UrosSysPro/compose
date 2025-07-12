package net.systemvi.configurator.components.configure.keyboard_keys.snaptap

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import arrow.core.Either
import arrow.core.flatten
import net.systemvi.configurator.components.configure.ConfigureViewModel
import net.systemvi.configurator.model.KeyMap
import net.systemvi.configurator.model.SnapTapPair

@Composable
fun SnapTapItem(keymap: KeyMap, pair: SnapTapPair){
    val first = keymap.keycaps.flatten().find { key-> key.matrixPosition==pair.first }!!.layers[0]
    val firstText=when (first) {
        is Either.Left -> first.value.name
        is Either.Right -> first.value.name
    }
    val second = keymap.keycaps.flatten().find { key-> key.matrixPosition==pair.second }!!.layers[0]
    val secondText=when (second) {
        is Either.Left -> second.value.name
        is Either.Right -> second.value.name
    }

    Row(
        modifier = Modifier
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.primaryContainer,RoundedCornerShape(8.dp))
            .padding(8.dp)
        ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        Text(firstText)
        Text(secondText)
        Divider()
        IconButton(onClick = {

        }){
            Icon(Icons.Filled.Delete,"delete snap tap pair")
        }
    }
}

@Composable
fun SnapTap(){
    val viewModel= viewModel { ConfigureViewModel() }
    val keymap=viewModel.keymapApi.keymap
    FlowRow(){
        FilledIconButton(onClick = {viewModel.currentlySelectingSnapTapPair = true}){
            Icon(Icons.Filled.Add,"add snap tap pair")
        }
        keymap.onSome { keymap->
            val snapTapPairs=keymap.snapTapPairs
            for(pair in snapTapPairs){
                SnapTapItem(keymap,pair)
            }
        }
    }
}