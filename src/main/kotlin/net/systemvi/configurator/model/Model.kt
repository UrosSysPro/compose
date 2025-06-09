package net.systemvi.configurator.model



enum class KeycapWidth(val size:Float){
    SIZE_1U(1f),
    SIZE_125U(1.25f),
    SIZE_15U(1f),
    SIZE_175U(1f),
    SIZE_2U(1f),
    SIZE_225U(1f),
    SIZE_275U(1f),
    SIZE_625U(1f),
}

enum class KeycapHeight(val size:Float){
    SIZE1U(1f),
    SIZE2U(2f),
}

data class KeycapOffset(val x:Float,val y:Float)

data class Key(val value:Byte, val name:String)

data class Keycap(
    val layers:List<Key>,
    val width:KeycapWidth,
    val height:KeycapHeight,
    val offset: KeycapOffset,
    val rotation:Float
)

data class Layout(val keycaps:List<List<Key>>)

//data class Macro()