package net.systemvi.configurator.utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import arrow.core.right
import jssc.SerialPort
import jssc.SerialPort.BAUDRATE_9600
import jssc.SerialPort.DATABITS_8
import jssc.SerialPort.PARITY_NONE
import jssc.SerialPort.STOPBITS_1
import jssc.SerialPortList
import net.systemvi.configurator.components.configure.KeycapPosition
import net.systemvi.configurator.model.Key
import net.systemvi.configurator.model.KeyMap
import net.systemvi.configurator.model.Keycap
import net.systemvi.configurator.model.KeycapHeight
import net.systemvi.configurator.model.KeycapMatrixPosition
import net.systemvi.configurator.model.KeycapWidth

object KeyboardSerialApi {
    private var selectedPortName by mutableStateOf<String?>(null)
    private var port by mutableStateOf<SerialPort?>(null)
    private var messageBuffer=listOf<Byte>()
    private var onKeymapRead:(keymap: KeyMap)->Unit={}

    fun getPortNames():List<String> = SerialPortList.getPortNames().toList()

    fun uploadKeycap(keycap: Keycap,key: Key,layer:Int) {
        val bytes: ByteArray = arrayOf(
            'l'.code.toByte(),
            keycap.matrixPosition.x.toByte(),
            keycap.matrixPosition.y.toByte(),
            layer.toByte(),
            key.value,
        ).toByteArray()
        port?.writeBytes(bytes)
    }

    fun selectPort(name:String?){
        selectedPortName=name
        if(port?.isOpened == true)port?.closePort()
        if(!selectedPortName.isNullOrEmpty()){
            port = SerialPort(name)
            port?.openPort()
            port?.setParams(BAUDRATE_9600,  DATABITS_8, STOPBITS_1, PARITY_NONE)
            port?.addEventListener { event ->
                val port=event.port
                val array: ByteArray = port.readBytes()?: ByteArray(0)
                messageBuffer=messageBuffer.plus(array.toList())
                checkForCommands()
            }
        }
    }

    fun requestKeymapRead(){
        port?.writeString("r")
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

    fun onKeymapRead(listener: (keymap: KeyMap) -> Unit) {
        onKeymapRead = listener
    }

    private fun processMessage(buffer:List<Byte>){
        val cmd = buffer[0].toInt().toChar()
        when(cmd){
            'l' -> onKeymapRead(readKeymapFromBuffer(buffer))
            else -> println("unknown cmd")
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
            val widht=keysBuffer[index+6].toInt()
            val height=keysBuffer[index+7].toInt()
            val physicalY=keysBuffer[index+8].toInt()
            val physicalX=keysBuffer[index+9].toInt()
            val active=keysBuffer[index+10].toInt()==1
            if(active)keycaps[physicalX][physicalY]= Keycap(
                layers = value.map { Key(it,"${it.toInt().toChar()}").right()},
                width = KeycapWidth.entries[widht],
                height = KeycapHeight.entries[height],
                matrixPosition = KeycapMatrixPosition(x,y)
            )
        }
//                this.keys=keys.map { it.map { key->key!! }.toList() }.toList().transpose()
        return KeyMap(keycaps.map {
            it.flatMap { key->
                if (key!=null)
                    listOf(key)
                else
                    emptyList()
            }.toList()
        }.toList())
    }

    fun closePort(){
        if(port?.isOpened == true)port?.closePort()
    }
}