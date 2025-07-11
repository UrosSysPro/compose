package net.systemvi.configurator.model

import arrow.optics.optics
import kotlinx.serialization.Serializable

@Serializable
@optics
data class KeycapOffset(val x:Float=0f, val y:Float=0f){companion object}
