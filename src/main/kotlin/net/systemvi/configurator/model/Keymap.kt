package net.systemvi.configurator.model

import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import arrow.core.left
import arrow.core.right
import arrow.core.some
import arrow.optics.dsl.index
import arrow.optics.optics
import eu.mihosoft.jcsg.CSG
import eu.mihosoft.jcsg.Cube
import eu.mihosoft.vvecmath.Vector3d
import kotlinx.serialization.Serializable
import java.io.FileWriter
import kotlin.collections.forEachIndexed
import kotlin.random.Random

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

private fun calculatePlateSize(keymap:KeyMap,oneUSize: Double):Pair<Double, Double>{
    var totalWidth=0.0
    var totalHeight=0.0
    var minSize=1.0
    var maxPadding=0.0
    var currentX=0.0
    var currentY=0.0

    keymap.keycaps.forEachIndexed { rowIndex, row ->
        row.forEachIndexed { keycapIndex, keycap ->
            val width=keycap.width.size.toDouble()
            val height=keycap.height.size.toDouble()
            val leftPadding=keycap.padding.left.toDouble()
            val bottomPadding=keycap.padding.bottom.toDouble()

            minSize=minSize.coerceAtMost(height)
            maxPadding=maxPadding.coerceAtLeast(bottomPadding)
            currentX+=oneUSize*(leftPadding+width)
        }
        totalWidth=totalWidth.coerceAtLeast(currentX)
        currentX=0.0
        currentY+=(minSize+maxPadding)*oneUSize
        totalHeight=totalHeight.coerceAtLeast(currentY)
        minSize=1.0
        maxPadding=0.0
    }
    return Pair(totalWidth,totalHeight)
}

private fun cutSwitchHolesFromPlate(keymap: KeyMap, oneUSize:Double,cut:(CSG)->Unit,switchHoleSize:Double=14.0) {
    var minSize = 1.0
    var maxPadding = 0.0
    var currentX = 0.0
    var currentY = 0.0
    var random = Random

    var switches: Option<CSG> = None

    keymap.keycaps.forEachIndexed { rowIndex, row ->
        row.forEachIndexed { keycapIndex, keycap ->
            val width = keycap.width.size.toDouble()
            val height = keycap.height.size.toDouble()
            val leftPadding = keycap.padding.left.toDouble()
            val bottomPadding = keycap.padding.bottom.toDouble()

            minSize = minSize.coerceAtMost(height)
            maxPadding = maxPadding.coerceAtLeast(bottomPadding)
            currentX += oneUSize * leftPadding

            val switchCube= Cube().apply{
                center = Vector3d.xyz(
                    currentX+oneUSize*width/2.0+random.nextDouble()*0.001,
                    currentY+oneUSize*height/2.0+random.nextDouble()*0.001,
                    0.0
                )
                dimensions = Vector3d.xyz(
                    switchHoleSize,
                    switchHoleSize,
                    switchHoleSize,
                )
            }.toCSG()

            switches=when(switches){
                is None->switchCube.some()
                is Some->(switches as Some<CSG>).value.union(switchCube).some()
            }

            currentX += oneUSize * width
        }
        currentX = 0.0
        currentY += (minSize + maxPadding) * oneUSize
        minSize = 1.0
        maxPadding = 0.0
    }
    switches.onSome { cut(it) }
}

fun exportTopPlate(fileName:String,keymap:KeyMap,oneUSize: Double,plateDepth:Double,switchHoleSize:Double) {

    val (plateWidth,plateHeight)=calculatePlateSize(keymap,oneUSize)

    var topPlate = Cube().apply {
        center = Vector3d.xyz(plateWidth/2.0, plateHeight/2.0, 0.0)
        dimensions = Vector3d.xyz(plateWidth,plateHeight,plateDepth)
    }.toCSG()

    cutSwitchHolesFromPlate(keymap,oneUSize,{topPlate=topPlate.difference(it)},switchHoleSize)

    val fileWriter = FileWriter(fileName)
    fileWriter.write(topPlate.toStlString())
    fileWriter.close()
}

fun exportCase(fileName:String,keymap:KeyMap,oneUSize:Double,borderWidth:Double,height:Double) {
    val (plateWidth,plateHeight)=calculatePlateSize(keymap,oneUSize)
    var case=Cube().apply{
        center=Vector3d.ZERO
        dimensions = Vector3d.xyz(
            plateWidth+2*borderWidth,
            plateHeight+2*borderWidth,
            height
        )
    }.toCSG()
    case=case.difference(Cube().apply {
        center = Vector3d.xyz(0.0,0.0,5.0)
        dimensions = Vector3d.xyz(plateWidth,plateHeight,height)
    }.toCSG())

    val fileWriter = FileWriter(fileName)
    fileWriter.write(case.toStlString())
    fileWriter.close()
}

fun KeyMap.exportStl(name:String){

    val oneUSize=19.0
    val switchSize=14.0
    val plateDepth=2.0

    exportTopPlate("top-plate.stl",this,oneUSize,plateDepth,switchSize)
    exportCase("case.stl",this,oneUSize,switchSize,20.0)
}