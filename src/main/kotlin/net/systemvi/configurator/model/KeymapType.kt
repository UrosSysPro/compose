package net.systemvi.configurator.model

import arrow.optics.optics
import kotlinx.serialization.Serializable

@Serializable
//@optics
sealed class KeymapType{
    @Serializable
    object Default : KeymapType()
    @Serializable
    class Onboard(val savedToFlash:Boolean): KeymapType(){companion object}
    @Serializable
    object Saved : KeymapType()
//    companion object
}