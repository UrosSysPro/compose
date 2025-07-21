package net.systemvi.configurator.utils.export.round_filet_design

import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import arrow.core.some
import eu.mihosoft.jcsg.CSG
import eu.mihosoft.jcsg.Cube
import eu.mihosoft.vvecmath.Vector3d
import net.systemvi.configurator.model.KeyMap
import net.systemvi.configurator.utils.syntax.sequence
import java.io.FileWriter
import kotlin.random.Random


data class SwitchSize(val width: Double, val height: Double)
data class KeycapSize(val width: Double, val height: Double)
data class KeycapPadding(val horizontal: Double, val vertical: Double)

class RoundFiletKeyboard(
    val keymap: KeyMap,
    val switchSize: SwitchSize,
    val keycapSize: KeycapSize,
    val keycapPadding: KeycapPadding,
    val plateHeight:Double,
    val keyboardBorderWidth:Double,
){

    fun exportPlate(fileName:String){
        var totalWidth=0.0
        var totalHeight=0.0
        var minSize = 1.0
        var maxPadding = 0.0
        var currentX = 0.0
        var currentY = 0.0
        var random = Random

        var oneUWidth=keycapSize.width+2*keycapPadding.horizontal
        var oneUHeight=keycapSize.height+2*keycapPadding.vertical

        var topPlate:Option<CSG> = None
        var switches: Option<CSG> = None

        keymap.keycaps.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { keycapIndex, keycap ->
                val width = keycap.width.size.toDouble()
                val height = keycap.height.size.toDouble()
                val leftPadding = keycap.padding.left.toDouble()
                val bottomPadding = keycap.padding.bottom.toDouble()

                minSize = minSize.coerceAtMost(height)
                maxPadding = maxPadding.coerceAtLeast(bottomPadding)
                currentX += oneUWidth * leftPadding

                val switchCube= Cube().apply{
                    center = Vector3d.xyz(
                        currentX+oneUWidth*width/2.0+random.nextDouble()*0.001,
                        currentY+oneUHeight*height/2.0+random.nextDouble()*0.001,
                        0.0
                    )
                    dimensions = Vector3d.xyz(
                        switchSize.width,
                        switchSize.height,
                        plateHeight*2,
                    )
                }.toCSG()

                switches=when(switches){
                    is None->switchCube.some()
                    is Some->(switches as Some<CSG>).value.union(switchCube).some()
                }

                currentX += oneUWidth * width
            }
            totalWidth=totalWidth.coerceAtLeast(currentX)
            currentX=0.0
            currentY+=(minSize+maxPadding)*oneUHeight
            totalHeight=totalHeight.coerceAtLeast(currentY)
            minSize=1.0
            maxPadding=0.0
        }

        topPlate=Cube().apply{
            center = Vector3d.xyz(totalWidth/2,totalHeight/2,0.0)
            dimensions = Vector3d.xyz(totalWidth,totalHeight,plateHeight)
        }.toCSG().some()

        Pair(topPlate,switches).sequence().onSome { (topPlate,switches) ->
            try{
                val writer=FileWriter(fileName)
                writer.write(topPlate.difference(switches).toStlString())
                writer.close()
            }catch (e:Exception){
                println("[ERROR] Error exporting rounded filet top plate")
                e.printStackTrace()
            }
        }
    }

    fun exportCase(fileName:String){

//        val (plateWidth,plateHeight)=calculatePlateSize(keymap,oneUSize)
//        var case=Cube().apply{
//            center=Vector3d.ZERO
//            dimensions = Vector3d.xyz(
//                plateWidth+2*borderWidth,
//                plateHeight+2*borderWidth,
//                height
//            )
//        }.toCSG()
//        case=case.difference(Cube().apply {
//            center = Vector3d.xyz(0.0,0.0,5.0)
//            dimensions = Vector3d.xyz(plateWidth,plateHeight,height)
//        }.toCSG())
//
//        val fileWriter = FileWriter(fileName)
//        fileWriter.write(case.toStlString())
//        fileWriter.close()
    }

    fun saveToDir(dirName: String){
        exportPlate("$dirName/plate.stl")
        exportCase("$dirName/plate.stl")
    }
}