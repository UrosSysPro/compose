package net.systemvi.configurator.components.neo_configure.keymap.keycap

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import arrow.core.Either
import arrow.core.getOrElse
import arrow.core.toOption
import net.systemvi.configurator.components.common.keyboard_grid.KeycapComponent
import net.systemvi.configurator.components.common.keycaps.FlatKeycap
import net.systemvi.configurator.components.neo_configure.NeoConfigureViewModel
import net.systemvi.configurator.model.changeName

val NeoConfigKeycap: KeycapComponent = { params ->
    val neoConfigViewModel = viewModel { NeoConfigureViewModel() }
    val keymap = neoConfigViewModel.keymap.getOrNull()!!
    val keycap=params.keycap

    val isPressed = neoConfigViewModel.currentlyPressedKeycaps.contains(keycap.matrixPosition)
    val isSelected = neoConfigViewModel.currentlySelectedKeycaps.contains(keycap.matrixPosition)

    val text=when(val a=keycap.layers
        .get(
            neoConfigViewModel.currentLayer.coerceAtMost(keycap.layers.size)
        )
    ){
        is Either.Left->a.value.name
        is Either.Right->a.value.name
    }

    val isLayer=keymap.layerKeyPositions.any{ it.position==keycap.matrixPosition }

    val layer=keymap.layerKeyPositions
        .find { it.position==keycap.matrixPosition }.toOption()
        .map { it.layer }
        .getOrElse { -1 }

    val isSnapTap = keymap.snapTapPairs.any { it.first == keycap.matrixPosition || it.second == keycap.matrixPosition }
    val snapTapIndex = keymap.snapTapPairs.indexOfFirst { it.first == keycap.matrixPosition || it.second == keycap.matrixPosition }
    val isSnapTapFirst = keymap.snapTapPairs.any { it.first == keycap.matrixPosition }

    when{
        isLayer -> LayerKeycap(isPressed,layer)
        isSnapTap -> SnapTapKeycap(isPressed,isSelected,text,isSnapTapFirst,snapTapIndex)
        else -> FlatKeycap(isPressed,isSelected,text)
    }
}