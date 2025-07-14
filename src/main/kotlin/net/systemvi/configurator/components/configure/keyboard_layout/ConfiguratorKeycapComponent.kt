package net.systemvi.configurator.components.configure.keyboard_layout

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import arrow.core.Either
import arrow.core.getOrElse
import arrow.core.toOption
import com.materialkolor.ktx.blend
import net.systemvi.configurator.components.common.keyboard_grid.KeycapComponent
import net.systemvi.configurator.components.configure.ConfigureViewModel
import net.systemvi.configurator.components.configure.CurrentlySelectedSnapTapKeys
import net.systemvi.configurator.data.LayerKeyColors
import net.systemvi.configurator.data.SnapTapKeyColors
import net.systemvi.configurator.data.modifierKeys
import net.systemvi.configurator.model.SnapTapPair

@OptIn(ExperimentalFoundationApi::class)
val ConfiguratorKeycapComponent: KeycapComponent=@Composable{ params->
    val viewModel= viewModel { ConfigureViewModel() }
    val keymap=viewModel.keymapApi.keymap.getOrNull()!!
    val layer=viewModel.selectedLayer().coerceAtMost(params.keycap.layers.size-1)
    val keycap=params.keycap
    val key=keycap.layers[layer]
    //user input
    val pressed=viewModel.currentlyPressedKeycaps.contains(keycap.matrixPosition)
    val selected=viewModel.isKeycapSelected(params.position.y,params.position.x)
    //layer keys
    val isLayerKey=keymap.layerKeyPositions.map { it.position }.contains(keycap.matrixPosition)
    val layerKeyLayer=keymap.layerKeyPositions.find { layerKey->layerKey.position==keycap.matrixPosition }?.layer?:-1
    //snap tap
    val isCurrentlySelectingSnapTapKeys=viewModel.currentlySelectingSnapTapPair
    val snapTapPair=keymap.snapTapPairs.find { it.first==keycap.matrixPosition || it.second==keycap.matrixPosition }.toOption()
    val isSnapTapKey=snapTapPair.isSome()
    val isFirstSnapTapKey=snapTapPair.map { it.first }.getOrNull()!=null
    val isSecondSnapTapKey=snapTapPair.map { it.second }.getOrNull()!=null
    val snapTapPairIndex=snapTapPair.map { keymap.snapTapPairs.indexOf(it) }.getOrElse { -1 }

    val onClick={
        if(isCurrentlySelectingSnapTapKeys){
            if(viewModel.currentlySelectedSnapTapKeys.first==null){
                viewModel.currentlySelectedSnapTapKeys.first=keycap.matrixPosition
            }else{
                viewModel.currentlySelectedSnapTapKeys.second=keycap.matrixPosition
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
        isCurrentlySelectingSnapTapKeys -> MaterialTheme.colorScheme.surfaceDim
        isLayerKey-> LayerKeyColors[layerKeyLayer-1].blend(Color.Black,when{
            pressed->0.1f
            selected->0.2f
            else-> 0f
        })
        pressed-> MaterialTheme.colorScheme.tertiaryContainer
        selected->MaterialTheme.colorScheme.primary
        else->MaterialTheme.colorScheme.secondaryContainer
    })

    val text=when{
        isLayerKey->"L${layerKeyLayer+1}"
        key is Either.Right->key.value.name
        key is Either.Left->key.value.name
        else -> {"[ERROR]"}
    }

    val borderColor by animateColorAsState(when{
        isSnapTapKey -> SnapTapKeyColors[snapTapPairIndex]
//        isLayerKey-> LayerKeyColors[layerKeyLayer-1].blend(Color.Black,when{
//            pressed->0.1f
//            selected->0.2f
//            else-> 0f
//        })
//        pressed-> MaterialTheme.colorScheme.tertiaryContainer
//        selected->MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.secondary
    })

    val borderWidth by animateDpAsState(when{
        isSnapTapKey->3.dp
        else->2.dp
    })

    Box(
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally,
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
                BorderStroke(borderWidth,borderColor),
                shape = RoundedCornerShape(10.dp)
            )
    ){
        Text(
            text,
            fontSize = MaterialTheme.typography.bodySmall.fontSize,
            modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center),
            textAlign = TextAlign.Center
        )
    }
}