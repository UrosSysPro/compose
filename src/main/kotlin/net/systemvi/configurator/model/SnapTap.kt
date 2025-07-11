package net.systemvi.configurator.model

import arrow.optics.optics
import kotlinx.serialization.Serializable

@Serializable
@optics
data class SnapTapPair(val first: KeycapMatrixPosition,val second: KeycapMatrixPosition)