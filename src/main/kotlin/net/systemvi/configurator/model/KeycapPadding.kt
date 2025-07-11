package net.systemvi.configurator.model

import arrow.optics.optics
import kotlinx.serialization.Serializable


@Serializable
@optics data class KeycapPadding(val bottom:Float = 0f, val left:Float = 0f){companion object}
