package net.systemvi.configurator.utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Matrix
import arrow.core.Either
import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import arrow.core.getOrElse
import arrow.core.left
import arrow.core.right
import arrow.core.some
import arrow.core.toOption
import jssc.SerialPort
import jssc.SerialPort.BAUDRATE_9600
import jssc.SerialPort.DATABITS_8
import jssc.SerialPort.PARITY_NONE
import jssc.SerialPort.STOPBITS_1
import jssc.SerialPortList
import net.systemvi.configurator.components.configure.KeycapPosition
import net.systemvi.configurator.data.allKeys
import net.systemvi.configurator.data.alphabetKeys
import net.systemvi.configurator.data.passKey
import net.systemvi.configurator.model.Key
import net.systemvi.configurator.model.KeyMap
import net.systemvi.configurator.model.Keycap
import net.systemvi.configurator.model.KeycapHeight
import net.systemvi.configurator.model.KeycapMatrixPosition
import net.systemvi.configurator.model.KeycapWidth
import net.systemvi.configurator.model.LayerKeyPosition
import net.systemvi.configurator.model.Macro
import net.systemvi.configurator.model.MacroAction
import net.systemvi.configurator.model.MacroActionType
import net.systemvi.configurator.model.SnapTapPair

class KeyboardSerialApi {
    private var selectedPortName by mutableStateOf<String?>(null)
    private var port by mutableStateOf<Option<SerialPort>>(None)
    private var messageBuffer=listOf<Byte>()
    private var onKeymapRead:(keymap: KeyMap)->Unit={}
    private var onKeycapPress:(keycapPosition: KeycapMatrixPosition)->Unit={}
    private var onKeycapRelease:(keycapPosition: KeycapMatrixPosition)->Unit={}

    fun getPortNames():List<String> = SerialPortList.getPortNames().toList()

    fun setKeyOnLayer(key:Key,layer:Int,matrixPosition: KeycapMatrixPosition){
        port.onSome { port->
            val bytes: ByteArray = arrayOf(
                'l'.code.toByte(),
                matrixPosition.x.toByte(),
                matrixPosition.y.toByte(),
                layer.toByte(),
                key.value,
            ).toByteArray()
            port.writeBytes(bytes)
        }.onNone {
            println("[ERROR] upload key called, and port is not opened")
        }
    }
    fun setKeyOnLayer(macro:Macro,layer:Int,matrixPosition: KeycapMatrixPosition){
        port.onSome { port->
            val bytes: ByteArray = arrayOf(
                'm'.code.toByte(),
                matrixPosition.x.toByte(),
                matrixPosition.y.toByte(),
                layer.toByte(),
                macro.actions.size.toByte(),
                *macro.actions.flatMap { (key, action) -> listOf(key.value,action.id.toByte()) }.toTypedArray(),
            ).toByteArray()
            port.writeBytes(bytes)
        }.onNone {
            println("[ERROR] upload macro called, and port is not opened")
        }
    }

    fun addLayerKeyPosition(position: KeycapMatrixPosition, layer:Int){
        port.onSome { port->
            val bytes: ByteArray = arrayOf(
                'A'.code.toByte(),
                position.x.toByte(),
                position.y.toByte(),
                layer.toByte(),
            ).toByteArray()
            port.writeBytes(bytes)
        }.onNone {
            println("[ERROR] upload macro called, and port is not opened")
        }
    }

    fun removeLayerKeyPosition(position: KeycapMatrixPosition){
        port.onSome { port->
            val bytes: ByteArray = arrayOf(
                'S'.code.toByte(),
                position.x.toByte(),
                position.y.toByte(),
            ).toByteArray()
            port.writeBytes(bytes)
        }.onNone {
            println("[ERROR] upload macro called, and port is not opened")
        }
    }

    fun addSnapTapPair(pair: SnapTapPair){
        port.onSome { port->
            val bytes: ByteArray = arrayOf(
                'E'.code.toByte(),
                pair.first.x.toByte(),
                pair.first.y.toByte(),
                pair.second.x.toByte(),
                pair.second.y.toByte(),
            ).toByteArray()
            port.writeBytes(bytes)
        }.onNone {
            println("[ERROR] upload macro called, and port is not opened")
        }
    }

    fun removeSnapTapPair(pair: SnapTapPair){
        println("[ERROR] remove snap tap not implemented")
    }

    fun selectPort(name:String?){
        closePort()
        if(name!=null){
            selectedPortName=name
            port = SerialPort(name).some()
            port.onSome { port->
                port.openPort()
                port.setParams(BAUDRATE_9600,  DATABITS_8, STOPBITS_1, PARITY_NONE)
                port.addEventListener { event ->
                    val port=event.port
                    val array: ByteArray = port.readBytes()?: ByteArray(0)
                    messageBuffer=messageBuffer.plus(array.toList())
                    checkForCommands()
                }
            }
        }
    }

    fun requestKeymapRead(){
        port.onSome { port -> port.writeString("r") }.onNone { println("[ERROR] requesting keymap read, no port opened") }
    }
    fun enableKeyPressEvents(){
        port.onSome { port->port.writeString("e") }.onNone { println("[ERROR] enable key press event, no port opened") }
    }
    fun disableKeyPressEvents(){
        port.onSome { port->port.writeString("d") }.onNone { println("[ERROR] disable key press event, no port opened") }
    }

    private fun checkForCommands(){
        while(true){
            var cmdFound=false
            for(i in 0 until messageBuffer.size){
                if(messageBuffer[i] == '@'.code.toByte()){
                    cmdFound=true
                    val message=messageBuffer.slice(0 until i)
                    val newMessageBuffer=messageBuffer.drop(i+1)
                    processMessage(message)
                    messageBuffer = newMessageBuffer
                    break
                }
            }
            if(!cmdFound)break
        }
    }

    fun onKeymapRead(listener: (KeyMap) -> Unit) {
        onKeymapRead = listener
    }
    fun onKeycapPress(listener: (KeycapMatrixPosition) -> Unit) {
        onKeycapPress = listener
    }
    fun onKeycapRelease(listener: (KeycapMatrixPosition) -> Unit) {
        onKeycapRelease = listener
    }

    private fun processMessage(buffer:List<Byte>){
        val cmd = buffer[0].toInt().toChar()
        when(cmd){
            'l' -> onKeymapRead(readKeymapFromBuffer2(buffer))
            'p' -> onKeycapPress(KeycapMatrixPosition(buffer[1].toInt(), buffer[2].toInt()))
            'r' -> onKeycapRelease(KeycapMatrixPosition(buffer[1].toInt(), buffer[2].toInt()))
            else -> println("[ERROR] unknown serial command: $cmd")
        }
    }

    private fun readKeymapFromBuffer(buffer:List<Byte>):KeyMap{
        val height=buffer[1].toInt()
        val width=buffer[2].toInt()
        val keycaps:MutableList<MutableList<Keycap?>> = MutableList(width){
            MutableList(height){
                null
            }
        }
        val keysBuffer=buffer.drop(3)
        val n=width*height
        for(i in 0 until n){
            val index=i*11
            val x=keysBuffer[index].toInt()
            val y=keysBuffer[index+1].toInt()
            val value=listOf(
                keysBuffer[index+2],
                keysBuffer[index+3],
                keysBuffer[index+4],
                keysBuffer[index+5],
            )
            val width=keysBuffer[index+6].toInt()
            val height=keysBuffer[index+7].toInt()
            val physicalY=keysBuffer[index+8].toInt()
            val physicalX=keysBuffer[index+9].toInt()
            val active=keysBuffer[index+10].toInt()==1
            if(active)keycaps[physicalX][physicalY]= Keycap(
                layers = value.map { value->
                    allKeys.find { key->key.value==value }.toOption().getOrElse { Key(value,"???") }.right()
                },
                width = KeycapWidth.entries[width],
                height = KeycapHeight.entries[height],
                matrixPosition = KeycapMatrixPosition(x,y)
            )
        }
        return KeyMap("untitled",keycaps.map {
            it.flatMap { key->
                if (key!=null)
                    listOf(key)
                else
                    emptyList()
            }.toList()
        }.toList())
    }

    private fun readKeymapFromBuffer2(buffer:List<Byte>):KeyMap{
        var buffer = buffer
        val columns=buffer[1].toInt()
        val rows=buffer[2].toInt()
        buffer=buffer.drop(3)
        val keycaps:MutableList<MutableList<Keycap?>> = MutableList(rows){
            MutableList(columns){
                null
            }
        }
        val layerKeyPositions = mutableListOf<LayerKeyPosition>()
        val snapTapPairs = mutableListOf<SnapTapPair>()

        while(buffer.isNotEmpty()){
            val type=buffer[0].toInt().toChar()
            when(type){
                'c'->{
                    val column=buffer[1].toInt()
                    val row=buffer[2].toInt()
                    val width=buffer[3].toInt()
                    val height=buffer[4].toInt()
                    val physicalX=buffer[5].toInt()
                    val physicalY=buffer[6].toInt()
                    buffer=buffer.drop(7)
                    val keys= MutableList<Either<Macro, Key>>(4){ passKey.right() }
                    for(i in 0 until 4){
                        val keyType= buffer[0].toInt().toChar()

                        when(keyType){
                            'n'->{
                                val key=buffer[1]
                                buffer=buffer.drop(2)
                                keys[i]=allKeys.find { it.value==key }.toOption().getOrElse { alphabetKeys.last() }.right()
                            }
                            'm'->{
                                val n=buffer[1].toInt()
                                val actions= MutableList(n){ MacroAction(passKey, if(it%2==0) MacroActionType.KEY_DOWN else MacroActionType.KEY_UP) }
                                buffer=buffer.drop(2)
                                for(i in 0 until n){
                                    val value=buffer[0]
                                    val type=buffer[1].toInt()
                                    actions[i]=MacroAction(allKeys.find { it.value==value }.toOption().getOrElse { alphabetKeys.last() },if (type==1)MacroActionType.KEY_DOWN else MacroActionType.KEY_UP)
                                    buffer=buffer.drop(2)
                                }
                                keys[i] = Macro("macro 0",actions.toList()).left()
                            }
                            else->{
                                println("[ERROR] unknown key type: $keyType")
                            }
                        }
                    }
                    keycaps[physicalY][physicalX] = Keycap(
                        layers=keys,
                        width=KeycapWidth.entries[width],
                        height=KeycapHeight.entries[height],
                        matrixPosition = KeycapMatrixPosition(column,row)
                    )
                }
                'l'->{
                    val column=buffer[1].toInt()
                    val row=buffer[2].toInt()
                    val layer=buffer[3].toInt()
                    buffer=buffer.drop(4)
                    layerKeyPositions += LayerKeyPosition(KeycapMatrixPosition(column,row),layer)
                }
                's'->{
                    val column0=buffer[1].toInt()
                    val row0=buffer[2].toInt()
                    val column1=buffer[3].toInt()
                    val row1=buffer[4].toInt()
                    buffer=buffer.drop(5)
                    snapTapPairs += SnapTapPair(KeycapMatrixPosition(column0,row0), KeycapMatrixPosition(column1,row1))
                }
            }
        }

        return KeyMap(
            name = "untitled",
            keycaps = keycaps.map {
                it.flatMap { key->
                    if (key!=null)
                        listOf(key)
                    else
                        emptyList()
                }.toList()
            }.toList(),
            layerKeyPositions = layerKeyPositions,
            snapTapPairs = snapTapPairs.filterIndexed { index, _ -> index%2 == 0 }
        ).apply { println(this.layerKeyPositions);println(this.snapTapPairs) }
    }

    fun closePort(){
        selectedPortName=null
        port.onSome { port-> if(port.isOpened){
            disableKeyPressEvents()
            port.closePort()
        } }
        port=None
    }
}