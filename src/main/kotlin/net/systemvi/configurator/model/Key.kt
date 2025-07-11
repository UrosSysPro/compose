package net.systemvi.configurator.model

import kotlinx.serialization.Serializable
import arrow.optics.*

@Serializable
@optics data class Key(val value:Byte, val name:String, val nativeCode:Long=0){companion object}
