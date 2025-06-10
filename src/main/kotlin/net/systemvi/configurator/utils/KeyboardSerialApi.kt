package net.systemvi.configurator.utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import jssc.SerialPort
import jssc.SerialPort.BAUDRATE_9600
import jssc.SerialPort.DATABITS_8
import jssc.SerialPort.PARITY_NONE
import jssc.SerialPort.STOPBITS_1
import jssc.SerialPortList
import net.systemvi.configurator.components.configure.KeycapPosition
import net.systemvi.configurator.model.Key
import net.systemvi.configurator.model.KeyMap

object KeyboardSerialApi {
    private var selectedPortName by mutableStateOf<String?>(null)
    private var port by mutableStateOf<SerialPort?>(null)
    private var messageBuffer=listOf<Byte>()
    private var onKeymapRead:(keymap: KeyMap)->Unit={}

    fun getPortNames():List<String> = SerialPortList.getPortNames().toList()

    fun uploadKeycap(position: KeycapPosition,key: Key,layer:Int) {
        val bytes: ByteArray = arrayOf(
            'k'.code.toByte(),
            ('0'.code + position.x).toByte(),
            ('0'.code + position.y).toByte(),
            key.value,
            0, 0, 0,
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
            'l'->{
                val width=buffer[1].toInt()
                val height=buffer[2].toInt()
//                val keys:MutableList<MutableList<ConfiguratorKey?>> = MutableList(width){
//                    MutableList(height){
//                        null
//                    }
//                }
//                val keysBuffer=buffer.drop(3)
//                val n=width*height
//                for(i in 0 until n){
//                    val index=i*6
//                    val x=keysBuffer[index].toInt()
//                    val y=keysBuffer[index+1].toInt()
//                    val value=listOf(
//                        keysBuffer[index+2].toInt().toChar(),
//                        keysBuffer[index+3].toInt().toChar(),
//                        keysBuffer[index+4].toInt().toChar(),
//                        keysBuffer[index+5].toInt().toChar(),
//                    )
//                    keys[x][y]= ConfiguratorKey(y*width+x,"${value[0]}",1f)
//                }
////                this.keys=keys.map { it.map { key->key!! }.toList() }.toList().transpose()
                onKeymapRead(KeyMap(emptyList()))
            }
            else -> println("unknown cmd")
        }
    }

    fun closePort(){
        if(port?.isOpened == true)port?.closePort()
    }
}