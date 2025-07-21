package net.systemvi.configurator.model

import arrow.core.left
import arrow.core.right
import arrow.optics.dsl.index
import arrow.optics.optics
import kotlinx.serialization.Serializable
import net.systemvi.configurator.utils.export.round_filet_design.KeycapSize
import net.systemvi.configurator.utils.export.round_filet_design.RoundFiletKeyboard
import net.systemvi.configurator.utils.export.round_filet_design.SwitchSize
import java.io.File

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

fun KeyMap.forEveryKeycapPositioned(keycapSize:KeycapSize,keycapPadding: net.systemvi.configurator.utils.export.round_filet_design.KeycapPadding,callback:(keycap: Keycap, rowIndex:Int, keycapIndex:Int, positionX: Double, positionY: Double)->Unit) {
    var minSize = 1.0
    var maxPadding = 0.0
    var currentX = 0.0
    var currentY = 0.0
    var oneUWidth = keycapSize.width + keycapPadding.horizontal * 2
    var oneUHeight = keycapSize.height + keycapPadding.vertical * 2

    this.keycaps.forEachIndexed { rowIndex, row ->
        row.forEachIndexed { keycapIndex, keycap ->
            val width = keycap.width.size.toDouble()
            val height = keycap.height.size.toDouble()
            val leftPadding = keycap.padding.left.toDouble()
            val bottomPadding = keycap.padding.bottom.toDouble()

            minSize = minSize.coerceAtMost(height)
            maxPadding = maxPadding.coerceAtLeast(bottomPadding)
            currentX += oneUWidth * leftPadding

            callback(keycap, rowIndex, keycapIndex, currentX, currentY)

            currentX += oneUWidth * width
        }
        currentX = 0.0
        currentY += (minSize + maxPadding) * oneUHeight
        minSize = 1.0
        maxPadding = 0.0
    }
}

fun KeyMap.exportStl(name:String){
    val dirName="round_filet_design"
    val file= File(dirName)
    file.mkdir()
    RoundFiletKeyboard(
        keymap=this,
        switchSize = SwitchSize(14.0,14.0),
        keycapSize = KeycapSize(18.0,18.0),
        keycapPadding = net.systemvi.configurator.utils.export.round_filet_design.KeycapPadding(0.5,0.5),
        plateHeight = 2.0,
        keyboardBorderWidth = 8.0,
        keyboardBorderHeight = 20.0,
    ).saveToDir(dirName)
}