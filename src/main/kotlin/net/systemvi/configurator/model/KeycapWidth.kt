package net.systemvi.configurator.model

import kotlinx.serialization.Serializable

@Serializable
enum class KeycapWidth(val size:Float){
    SIZE_1U(1f),
    SIZE_125U(1.25f),
    SIZE_15U(1.5f),
    SIZE_175U(1.75f),
    SIZE_2U(2f),
    SIZE_225U(2.25f),
    SIZE_275U(2.75f),
    SIZE_625U(6.25f);
}
