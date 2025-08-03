package net.systemvi.configurator.components.neo_configure.keymap.keycap

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.isCtrlPressed
import androidx.compose.ui.input.pointer.pointerInput
import androidx.lifecycle.viewmodel.compose.viewModel
import arrow.core.Either
import arrow.core.getOrElse
import arrow.core.toOption
import net.systemvi.configurator.components.common.keyboard_grid.KeycapComponent
import net.systemvi.configurator.components.common.keycaps.FlatKeycap
import net.systemvi.configurator.components.neo_configure.NeoConfigureViewModel

val NeoConfigKeycap: KeycapComponent = { params ->
    val neoConfigViewModel = viewModel { NeoConfigureViewModel() }
    val keymap = neoConfigViewModel.keymap.getOrNull()!!
    val keycap=params.keycap
    val currentSelectedLayer=neoConfigViewModel.currentLayer.coerceAtMost(keycap.layers.size-1)

    val isPressed = neoConfigViewModel.currentlyPressedKeycaps.contains(keycap.matrixPosition)
    val isSelected = neoConfigViewModel.currentlySelectedKeycaps.contains(params.position)

    val text=when(val a=keycap.layers.get(currentSelectedLayer)){
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

    val isSelectingSnapTap by neoConfigViewModel.snapTapSelection.isSelecting
    val firstSelectedSnapTapKey by neoConfigViewModel.snapTapSelection.first
    val secondSelectedSnapTapKey by neoConfigViewModel.snapTapSelection.second
    val isFirstSelectedSnapTapKey = firstSelectedSnapTapKey.map { it==keycap.matrixPosition }.getOrElse { false }
    val isSecondSelectedSnapTapKey = firstSelectedSnapTapKey.map { it==keycap.matrixPosition }.getOrElse { false }

    Box(
        modifier = Modifier.pointerInput(Unit) {
            awaitPointerEventScope {
                while (true) {
                    val event = awaitPointerEvent()
                    if (event.type == PointerEventType.Press) {
                        val ctrlPressed = event.keyboardModifiers.isCtrlPressed
                        neoConfigViewModel.keycapClick(params.position,ctrlPressed)
                    }
                }
            }
        }
    ) {
        when{
            isSelectingSnapTap && isSnapTap -> SnapTapKeycap(isPressed,isSelected,text,isSnapTapFirst,snapTapIndex)
            isSelectingSnapTap && isFirstSelectedSnapTapKey -> SnapTapKeycap(isPressed,isSelected,text,true,0)
            isSelectingSnapTap && isSecondSelectedSnapTapKey -> SnapTapKeycap(isPressed,isSelected,text,false,0)
            isSelectingSnapTap  -> SnaptapSelectionKeycap(text)
            isLayer -> LayerKeycap(isPressed,layer)
            isSnapTap -> SnapTapKeycap(isPressed,isSelected,text,isSnapTapFirst,snapTapIndex)
            else -> FlatKeycap(isPressed,isSelected,text)
        }
    }
}