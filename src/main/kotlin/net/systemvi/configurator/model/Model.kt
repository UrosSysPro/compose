package net.systemvi.configurator.model

sealed interface Either<A,B>
data class Left<A,B>(val value: A): Either<A,B>
data class Right<A,B>(val value: B): Either<A,B>

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
    val layers:List<Either<Macro,Key>>,
    val width:KeycapWidth,
    val height:KeycapHeight,
    val offset: KeycapOffset,
    val rotation:Float
)

enum class MacroActionType(val id:Int){
   KEY_UP(1),KEY_DOWN(0)
}
data class MacroAction(val key:Key,val action:MacroActionType)
data class Macro(val actions:List<MacroAction>)

data class KeyMap(val keycaps:List<List<Keycap>>)