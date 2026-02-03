package net.systemvi.configurator.utils.export.round_filet_design
//
//import arrow.core.None
//import arrow.core.Option
//import arrow.core.Some
//import arrow.core.some
//import eu.mihosoft.jcsg.CSG
//import eu.mihosoft.jcsg.Cube
//import eu.mihosoft.jcsg.Cylinder
//import eu.mihosoft.vvecmath.Transform
//import eu.mihosoft.vvecmath.Vector3d
//import net.systemvi.configurator.model.KeyMap
//import net.systemvi.configurator.model.forEveryKeycapPositioned
//import net.systemvi.configurator.utils.syntax.sequence
//import java.io.FileWriter
//import kotlin.random.Random
//
//
data class SwitchSize(val width: Double, val height: Double)
data class KeycapSize(val width: Double, val height: Double)
data class KeycapPadding(val horizontal: Double, val vertical: Double)
//
//class RoundFiletKeyboard(
//    val keymap: KeyMap,
//    val switchSize: SwitchSize,
//    val keycapSize: KeycapSize,
//    val keycapPadding: KeycapPadding,
//    val plateHeight:Double,
//    val keyboardBorderWidth:Double,
//    val keyboardBorderHeight:Double,
//){
//
//    fun exportPlate(fileName:String){
//        var totalWidth=0.0
//        var totalHeight=0.0
//        var oneUWidth=keycapSize.width+2*keycapPadding.horizontal
//        var oneUHeight=keycapSize.height+2*keycapPadding.vertical
//        var topPlate:Option<CSG> = None
//        var switches: Option<CSG> = None
//        var random = Random
//
//        keymap.forEveryKeycapPositioned(keycapSize,keycapPadding){ keycap, rowIndex, keycapIndex, positionX, positionY ->
//            val width = keycap.width.size.toDouble()
//            val height = keycap.height.size.toDouble()
//
//            val switchCube= Cube().apply{
//                center = Vector3d.xyz(
//                    positionX+oneUWidth*width/2.0+random.nextDouble()*0.001,
//                    positionY+oneUHeight*height/2.0+random.nextDouble()*0.001,
//                    0.0
//                )
//                dimensions = Vector3d.xyz(
//                    switchSize.width,
//                    switchSize.height,
//                    plateHeight*2,
//                )
//            }.toCSG()
//
//            switches=when(switches){
//                is None->switchCube.some()
//                is Some->(switches as Some<CSG>).value.union(switchCube).some()
//            }
//            totalWidth=totalWidth.coerceAtLeast(positionX+oneUWidth*width)
//            totalHeight=totalHeight.coerceAtLeast(positionY+oneUHeight*height)
//        }
//
//        topPlate=Cube().apply{
//            center = Vector3d.xyz(totalWidth/2,totalHeight/2,0.0)
//            dimensions = Vector3d.xyz(totalWidth,totalHeight,plateHeight)
//        }.toCSG().some()
//
//        Pair(topPlate,switches).sequence().onSome { (topPlate,switches) ->
//            try{
//                val writer=FileWriter(fileName)
//                writer.write(topPlate.difference(switches).toStlString())
//                writer.close()
//            }catch (e:Exception){
//                println("[ERROR] Error exporting rounded filet top plate")
//                e.printStackTrace()
//            }
//        }
//    }
//
//    fun exportCase(fileName:String){
//        var totalWidth=0.0
//        var totalHeight=0.0
//        var oneUWidth=keycapSize.width+2*keycapPadding.horizontal
//        var oneUHeight=keycapSize.height+2*keycapPadding.vertical
//        keymap.forEveryKeycapPositioned(keycapSize,keycapPadding){ keycap, rowIndex, keycapIndex, positionX, positionY ->
//            val width = keycap.width.size.toDouble()
//            val height = keycap.height.size.toDouble()
//            totalWidth=totalWidth.coerceAtLeast(positionX+oneUWidth*width)
//            totalHeight=totalHeight.coerceAtLeast(positionY+oneUHeight*height)
//        }
//        fun cornerCylinder(x: Double,y: Double):CSG{
//
//            val topCylinder = Cylinder(
//                Vector3d.z(3.0),
//                Vector3d.z(0.0),
//                keyboardBorderWidth-3.0,
//                keyboardBorderWidth,
//                32
//            ).toCSG().transformed(Transform.unity().translate(Vector3d.z(keyboardBorderHeight/2)))
//            val bottomCylinder= Cylinder(
//                Vector3d.z(-3.0),
//                Vector3d.z(0.0),
//                keyboardBorderWidth-3.0,
//                keyboardBorderWidth,
//                32
//            ).toCSG().transformed(Transform.unity().translate(Vector3d.z(-keyboardBorderHeight/2)))
//
//            val midCylinder=Cylinder(
//                Vector3d.z(-keyboardBorderHeight/2),
//                Vector3d.z(keyboardBorderHeight/2),
//                keyboardBorderWidth,
//                keyboardBorderWidth,
//                32
//            ).toCSG()
//
//            val cylinder=midCylinder.union(
//                topCylinder,
//                bottomCylinder
//            )
//
//            return cylinder.transformed(Transform.unity().translate(Vector3d.xy(x,y)))
//        }
//
//        val outerBox={
//            val corners=mutableListOf(
//                cornerCylinder(-totalWidth/2,-totalHeight/2),
//                cornerCylinder( totalWidth/2,-totalHeight/2),
//                cornerCylinder(-totalWidth/2, totalHeight/2),
//                cornerCylinder( totalWidth/2, totalHeight/2),
//            )
//            corners[0]=corners[0].union(corners[1])
//            corners[0]=corners[0].union(corners[2])
//            corners[0]=corners[0].union(corners[3])
//            corners[0]=corners[0].hull()
//            corners[0]
//        }()
//        val innerBox=Cube(Vector3d.ZERO,Vector3d.xyz(totalWidth,totalHeight,keyboardBorderHeight*2)).toCSG()
//        val border=outerBox.difference(innerBox)
//        try {
//            val writer=FileWriter(fileName)
//            writer.write(border.toStlString())
//            writer.close()
//        }catch (e:Exception){
//            println("[ERROR] error exporting rounded filet case")
//            e.printStackTrace()
//        }
//    }
//
//    fun saveToDir(dirName: String){
//        exportPlate("$dirName/plate.stl")
//        exportCase("$dirName/case.stl")
//    }
//}