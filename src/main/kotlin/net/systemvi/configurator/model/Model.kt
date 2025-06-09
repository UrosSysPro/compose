package net.systemvi.configurator.model

enum class KeycapSize(val size:Float){
    SIZE1U(1f)
}
data class Key(val value:Byte, val name:String)
data class Keycap(val key:Key)
//data class Layout()
//data class Macro()