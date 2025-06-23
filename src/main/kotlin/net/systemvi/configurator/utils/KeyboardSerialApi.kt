package net.systemvi.configurator.utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import arrow.core.getOrElse
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
import net.systemvi.configurator.model.Key
import net.systemvi.configurator.model.KeyMap
import net.systemvi.configurator.model.Keycap
import net.systemvi.configurator.model.KeycapHeight
import net.systemvi.configurator.model.KeycapMatrixPosition
import net.systemvi.configurator.model.KeycapWidth

class KeyboardSerialApi {
    private var selectedPortName by mutableStateOf<String?>(null)
    private var port by mutableStateOf<Option<SerialPort>>(None)
    private var messageBuffer=listOf<Byte>()
    private var onKeymapRead:(keymap: KeyMap)->Unit={}
    private var onKeycapPress:(keycapPosition: KeycapMatrixPosition)->Unit={}
    private var onKeycapRelease:(keycapPosition: KeycapMatrixPosition)->Unit={}

    fun getPortNames():List<String> = SerialPortList.getPortNames().toList()

    fun uploadKeycap(keycap: Keycap,key: Key,layer:Int) {
        port.onSome { port->
            val bytes: ByteArray = arrayOf(
                'l'.code.toByte(),
                keycap.matrixPosition.x.toByte(),
                keycap.matrixPosition.y.toByte(),
                layer.toByte(),
                key.value,
            ).toByteArray()
            port.writeBytes(bytes)
        }.onNone {
            println("[ERROR] upload key called, and port is not opened")
        }
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
            'l' -> onKeymapRead(readKeymapFromBuffer(buffer))
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

    fun closePort(){
        selectedPortName=null
        port.onSome { port-> if(port.isOpened){
            disableKeyPressEvents()
            port.closePort()
        } }
        port=None
    }
}