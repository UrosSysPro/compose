package net.systemvi.configurator.model

import arrow.core.Either
import arrow.core.right
import arrow.optics.*
import arrow.optics.dsl.index
import arrow.optics.typeclasses.Index

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


enum class KeycapHeight(val size:Float){
    SIZE1U(1f),
    SIZE2U(2f);
}

@optics data class KeycapOffset(val x:Float=0f, val y:Float=0f){companion object}

@optics data class Key(val value:Byte, val name:String){companion object}

@optics data class KeycapMatrixPosition(val x:Int,val y:Int){companion object}

@optics data class Keycap(
    val layers:List<Either<Macro,Key>>,
    val width:KeycapWidth= KeycapWidth.SIZE_1U,
    val height:KeycapHeight= KeycapHeight.SIZE1U,
    val offset: KeycapOffset= KeycapOffset(),
    val rotation:Float=0f,
    val matrixPosition:KeycapMatrixPosition = KeycapMatrixPosition(0,0),
){companion object}

fun Keycap.overrideWidth(width: KeycapWidth): Keycap = Keycap.width.modify(this){ width }
fun Keycap.overrideHeight(height: KeycapHeight): Keycap = Keycap.height.modify(this){ height }

enum class MacroActionType(val id:Int){
    KEY_UP(1),KEY_DOWN(0);
}
@optics data class MacroAction(val key:Key,val action:MacroActionType){companion object}
@optics data class Macro(val actions:List<MacroAction>){companion object}

@optics data class KeyMap(val keycaps:List<List<Keycap>>){companion object}

fun KeyMap.setKeyWidth(i:Int,j:Int,width:KeycapWidth): KeyMap=
    KeyMap.keycaps
        .index(Index.list(),i)
        .index(Index.list(),j)
        .set(this,this.keycaps[i][j].overrideWidth(width))

fun KeyMap.updateKeycap(x:Int,y:Int,layer:Int,key:Key): KeyMap=
    KeyMap.keycaps
        .index(Index.list(),x)
        .index(Index.list(),y)
        .layers.index(Index.list(),layer)
        .set(this,key.right())
