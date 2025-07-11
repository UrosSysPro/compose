package net.systemvi.configurator.model

import kotlinx.serialization.Serializable


@Serializable
enum class KeycapHeight(val size:Float){
    SIZE1U(1f),
    SIZE2U(2f);
}
