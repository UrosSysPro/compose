package net.systemvi.configurator.model

import arrow.optics.optics
import kotlinx.serialization.Serializable

@Serializable
@optics
data class LayerKeyPosition(val position: KeycapMatrixPosition,val layer:Int)