package net.systemvi.configurator.model

import arrow.optics.optics
import kotlinx.serialization.Serializable


@Serializable
@optics data class KeycapMatrixPosition(val x:Int,val y:Int){companion object}
