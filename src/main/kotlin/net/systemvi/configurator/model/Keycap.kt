package net.systemvi.configurator.model

import arrow.core.Either
import arrow.optics.optics
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable


@Serializable
@optics data class Keycap(
    val layers:List<@Contextual Either<Macro,Key>>,
    val width:KeycapWidth= KeycapWidth.SIZE_1U,
    val height:KeycapHeight= KeycapHeight.SIZE1U,
    val offset: KeycapOffset= KeycapOffset(),
    val padding: KeycapPadding = KeycapPadding(),
    val rotation:Float=0f,
    val matrixPosition:KeycapMatrixPosition = KeycapMatrixPosition(0,0),
){companion object}

fun Keycap.overrideWidth(width: KeycapWidth): Keycap = Keycap.width.modify(this){ width }

fun Keycap.overrideHeight(height: KeycapHeight): Keycap = Keycap.height.modify(this){ height }

fun Keycap.overrideLeftPadding(padding: KeycapPadding): Keycap = Keycap.padding.left.modify(this){ padding.left }

fun Keycap.overrideBottomPadding(padding: KeycapPadding): Keycap = Keycap.padding.bottom.modify(this){ padding.bottom }
