package net.systemvi.configurator.model

import arrow.optics.optics
import kotlinx.serialization.Serializable


@Serializable
@optics data class KeycapMatrixPosition(val column:Int, val row:Int){companion object}
