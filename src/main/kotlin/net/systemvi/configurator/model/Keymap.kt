package net.systemvi.configurator.model

import arrow.core.left
import arrow.core.right
import arrow.optics.dsl.index
import arrow.optics.optics
import arrow.optics.typeclasses.Index
import eu.mihosoft.jcsg.Cube
import kotlinx.serialization.Serializable
import net.systemvi.configurator.components.configure.KeycapPosition
import java.io.FileWriter
import javax.swing.text.Keymap

@Serializable
@optics data class KeyMap(
    val name:String,
    val keycaps:List<List<Keycap>>,
    val layerKeyPositions:List<LayerKeyPosition> = emptyList(),
    val snapTapPairs:List<SnapTapPair> = emptyList(),
){companion object}

fun KeyMap.changeName(name: String): KeyMap =
    KeyMap.name.set(this, name)

fun KeyMap.setKeycapWidth(i:Int, j:Int, width:KeycapWidth): KeyMap=
    KeyMap.keycaps
        .index(i)
        .index(j)
        .set(this,this.keycaps[i][j].overrideWidth(width))

fun KeyMap.setKeycapHeight(i:Int, j:Int, height: KeycapHeight): KeyMap =
    KeyMap.keycaps
        .index(i)
        .index(j)
        .set(this, this.keycaps[i][j].overrideHeight(height))

fun KeyMap.setKeycapLeftPadding(i:Int, j:Int, padding: KeycapPadding): KeyMap =
    KeyMap.keycaps
        .index(i)
        .index(j)
        .set(this, this.keycaps[i][j].overrideLeftPadding(padding))

fun KeyMap.setKeycapBottomPadding(i:Int, j:Int, padding: KeycapPadding): KeyMap =
    KeyMap.keycaps
        .index(i)
        .index(j)
        .set(this, this.keycaps[i][j].overrideBottomPadding(padding))

fun KeyMap.updateKeycap(x:Int,y:Int,layer:Int,key:Key): KeyMap=
    KeyMap.keycaps
        .index(x)
        .index(y)
        .layers
        .index(layer)
        .set(this,key.right())

fun KeyMap.updateKeycap(x:Int,y:Int,layer:Int,macro:Macro): KeyMap=
    KeyMap.keycaps
        .index(x)
        .index(y)
        .layers
        .index(layer)
        .set(this,macro.left())

fun KeyMap.addLayerKey(layerKeyPosition: LayerKeyPosition): KeyMap=
    KeyMap.layerKeyPositions.modify(this) { layerkeys -> layerkeys + layerKeyPosition }

fun KeyMap.removeLayerKey(position: KeycapMatrixPosition): KeyMap=
    KeyMap.layerKeyPositions.modify(this) { layerkeys -> layerkeys.filter { it.position!=position } }

fun KeyMap.addSnapTapPair(pair: SnapTapPair): KeyMap=
    KeyMap.snapTapPairs.modify(this) { snappairs -> snappairs + pair }

fun KeyMap.removeSnapTapPair(pair: SnapTapPair): KeyMap=
    KeyMap.snapTapPairs.modify(this) { snappairs -> snappairs.filter { it != pair } }

fun KeyMap.exportStl(name:String){
    val topPlate= Cube(30.0)
    val fileWriter= FileWriter(name)
    fileWriter.write(topPlate.toCSG().toStlString())
    fileWriter.close()
}