package net.systemvi.configurator.components.configure.keyboard_layout

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import arrow.core.Either
import arrow.core.getOrElse
import arrow.core.toOption
import net.systemvi.configurator.components.common.keyboard_grid.KeycapComponent
import net.systemvi.configurator.components.common.keycaps.ConfiguratorKeycap
import net.systemvi.configurator.components.configure.ConfigureViewModel
import net.systemvi.configurator.components.configure.CurrentlySelectedSnapTapKeys
import net.systemvi.configurator.model.SnapTapPair

@OptIn(ExperimentalFoundationApi::class)
val ConfiguratorKeycapComponent: KeycapComponent=@Composable{ params->
    val viewModel= viewModel { ConfigureViewModel() }
    val keymap=viewModel.keymap.getOrNull()!!
    val layer=viewModel.selectedLayer().coerceAtMost(params.keycap.layers.size-1)
    val keycap=params.keycap
    val key=keycap.layers[layer]
    //user input
    val pressed=viewModel.currentlyPressedKeycaps.contains(keycap.matrixPosition)
    val selected=viewModel.isKeycapSelected(params.position.column,params.position.row)
    //layer keys
    val isLayerKey=keymap.layerKeyPositions.map { it.position }.contains(keycap.matrixPosition)
    val layerKeyLayer=keymap.layerKeyPositions.find { layerKey->layerKey.position==keycap.matrixPosition }?.layer?:-1
    //snap tap
    val isCurrentlySelectingSnapTapKeys=viewModel.currentlySelectingSnapTapPair
    val snapTapPair=keymap.snapTapPairs.find { it.first==keycap.matrixPosition || it.second==keycap.matrixPosition }.toOption()
    val isSnapTapKey=snapTapPair.isSome()
    val isFirstSnapTapKey=snapTapPair.map { it.first==keycap.matrixPosition }.getOrNull()?:false
    val isSecondSnapTapKey=snapTapPair.map { it.second==keycap.matrixPosition }.getOrNull()?:false
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
            viewModel.selectKeycap(params.position.column,params.position.row)
        }
    }

    val text=when{
        isLayerKey->"L${layerKeyLayer+1}"
        key is Either.Right->key.value.name
        key is Either.Left->key.value.name
        else -> {"[ERROR]"}
    }

    ConfiguratorKeycap(
        isDown = pressed,
        isSelected = selected,
        onClick = onClick,
        isLayerKey=isLayerKey,
        layer=layerKeyLayer,
        isSnapTapKey=isSnapTapKey,
        isFirstSnapTapKey=isFirstSnapTapKey,
        isCurrentlySelectingSnapTapKeys=isCurrentlySelectingSnapTapKeys,
        snapTapPairIndex = snapTapPairIndex,
        text = text,
    )
//    ElevatedKeycap(pressed,selected,text)
//    RGBWaveKeycap(pressed,0,text)
}