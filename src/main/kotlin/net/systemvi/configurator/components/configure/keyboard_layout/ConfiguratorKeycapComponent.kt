package net.systemvi.configurator.components.configure.keyboard_layout

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import arrow.core.Either
import com.materialkolor.ktx.blend
import net.systemvi.configurator.components.common.keyboard_grid.KeycapComponent
import net.systemvi.configurator.components.configure.ConfigureViewModel
import net.systemvi.configurator.components.configure.CurrentlySelectedSnapTapKeys
import net.systemvi.configurator.data.LayerKeyColors
import net.systemvi.configurator.model.SnapTapPair

@OptIn(ExperimentalFoundationApi::class)
val ConfiguratorKeycapComponent: KeycapComponent=@Composable{ params->
    val viewModel= viewModel { ConfigureViewModel() }
    val layer=viewModel.selectedLayer().coerceAtMost(params.keycap.layers.size-1)
    val pressed=viewModel.currentlyPressedKeycaps.contains(params.keycap.matrixPosition)
    val selected=viewModel.isKeycapSelected(params.position.y,params.position.x)
    val keymap=viewModel.keymapApi.keymap.getOrNull()!!
    val isLayerKey=keymap.layerKeyPositions.map { it.position }.contains(params.keycap.matrixPosition)
    val layerKeyLayer=keymap.layerKeyPositions.find { layerKey->layerKey.position==params.keycap.matrixPosition }?.layer?:-1
    val key=params.keycap.layers[layer]

    val text=when{
        isLayerKey->"L${layerKeyLayer+1}"
        key is Either.Right->key.value.name
        key is Either.Left->key.value.name
        else -> {"[ERROR]"}
    }


    val onClick={
        if(viewModel.currentlySelectingSnapTapPair){
            if(viewModel.currentlySelectedSnapTapKeys.first==null){
                viewModel.currentlySelectedSnapTapKeys.first=params.keycap.matrixPosition
            }else{
                viewModel.currentlySelectedSnapTapKeys.second=params.keycap.matrixPosition
                viewModel.setSnapTapPair(SnapTapPair(
                    viewModel.currentlySelectedSnapTapKeys.first!!,
                    viewModel.currentlySelectedSnapTapKeys.second!!,
                ))
                viewModel.currentlySelectedSnapTapKeys = CurrentlySelectedSnapTapKeys()
                viewModel.currentlySelectingSnapTapPair=false
            }
        }else{
            viewModel.selectKeycap(params.position.y,params.position.x)
        }
    }

    val backgroundColor by animateColorAsState(when{
        isLayerKey-> LayerKeyColors[layerKeyLayer-1].blend(Color.Black,when{
            pressed->0.1f
            selected->0.2f
            else-> 0f
        })
        pressed-> MaterialTheme.colorScheme.tertiaryContainer
        selected->MaterialTheme.colorScheme.primary
        else->MaterialTheme.colorScheme.secondaryContainer
    })

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(2.dp)
            .clip(RoundedCornerShape(10.dp))
            .onClick(onClick = onClick)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(10.dp)
            )
            .border(
                BorderStroke(2.dp,MaterialTheme.colorScheme.secondary),
                shape = RoundedCornerShape(10.dp)
            )
    ){
        Text(
            text,
            fontSize = MaterialTheme.typography.bodySmall.fontSize,
        )
    }
}