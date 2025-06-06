package net.systemvi.configurator.components.configure

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import jssc.SerialPort.*
import jssc.*
import net.systemvi.configurator.components.configure.keyboard_layout.ConfiguratorKey

fun <T>List<List<T>>.transpose(): List<List<T>> {
    return (this[0].indices).map { i -> (this.indices).map { j -> this[j][i] } }
}

class ConfigureViewModel(): ViewModel() {

    var serialPortNames by mutableStateOf(listOf<String>())
    var selectedPortName by mutableStateOf<String?>(null)
    var port by mutableStateOf<SerialPort?>(null)
    var messageBuffer=listOf<Byte>()

    var keys by mutableStateOf( {
        val row0 = "` 1 2 3 4 5 6 7 8 9 0 - = Back"
        val row1 = "Tab q w e r t y u i o p [ ] \\"
        val row2 = "Caps a s d f g h j k l ; ' Enter"
        val row3 = "Shift z x c v b n m , . / Shift"
        val row4 = "Ctrl Win Alt Space Fn Win Alt Ctrl"
        val rows = listOf(row0, row1, row2, row3, row4)

        val keys= rows.zip(rows.indices).map { (row, j) ->
            row.split(" ").zip(row.split(" ").indices).map { (key, i) ->
                ConfiguratorKey(j * 100 + i, key, 1.0f)
            }
        }
        keys[0].last().size=2f

        keys[1][0].size=1.25f // tab
        keys[1].last().size=1.75f // tab

        keys[2][0].size=1.5f // caps
        keys[2].last().size=2.5f // enter

        keys[3][0].size=2f  // left shift
        keys[3].last().size=3f  // left shift

        keys[4][0].size=1.25f //ctrl
        keys[4][1].size=1.25f //win
        keys[4][2].size=1.25f //alt
        keys[4][3].size=6.25f //space
        keys[4][4].size=1.25f //fn
        keys[4][5].size=1.25f //win
        keys[4][6].size=1.25f //alt
        keys[4][7].size=1.25f //ctrl
        keys
    }())

    var selectedKey by mutableStateOf<ConfiguratorKey?>(null)

    fun setKeyValue(keyId:Int,value:String){
        keys=keys.map { it.map { key->if (key.id==keyId) ConfiguratorKey(keyId,value,key.size) else key } }
        for(row in keys){
            for(key in row){
                if(key.id==keyId){
                    selectedKey=key
                }
            }
        }
        keys.forEachIndexed { i, row ->
           row.forEachIndexed { j, key ->
               if(port?.isOpened == true){
                   if(keyId==key.id){
                       val bytes: ByteArray = arrayOf(
                           'k'.code.toByte(),
                           ('0'.code+j).toByte(),
                           ('0'.code+i).toByte(),
                           value[0].code.toByte(),
                           0,0,0,
                       ).toByteArray()
                       port?.writeBytes(bytes)
                   }
               }
           }
        }
    }

    fun readPortNames(){
        serialPortNames=SerialPortList.getPortNames().toList()
    }

    fun selectPort(name:String?){
        selectedPortName=name
        if(port?.isOpened == true)port?.closePort()
        if(!selectedPortName.isNullOrEmpty()){
            port = SerialPort(name)
            port?.openPort()
            port?.setParams(BAUDRATE_9600,  DATABITS_8, STOPBITS_1, PARITY_NONE)
            keys=listOf()
            port?.addEventListener { event ->
                val port=event.port
                val array: ByteArray = port.readBytes()?: ByteArray(0)
                messageBuffer=messageBuffer.plus(array.toList())
                checkForCommands()
            }
            port?.writeString("r")
        }
    }

    fun checkForCommands(){
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

    fun processMessage(buffer:List<Byte>){
        val cmd = buffer[0].toInt().toChar()
        when(cmd){
            'l'->{
                val width=buffer[1].toInt()
                val height=buffer[2].toInt()
                val keys:MutableList<MutableList<ConfiguratorKey?>> = MutableList(width){
                    MutableList(height){
                        null
                    }
                }
                val keysBuffer=buffer.drop(3)
                val n=width*height
                for(i in 0 until n){
                    val index=i*6
                    val x=keysBuffer[index].toInt()
                    val y=keysBuffer[index+1].toInt()
                    val value=listOf(
                        keysBuffer[index+2].toInt().toChar(),
                        keysBuffer[index+3].toInt().toChar(),
                        keysBuffer[index+4].toInt().toChar(),
                        keysBuffer[index+5].toInt().toChar(),
                    )
                    keys[x][y]= ConfiguratorKey(y*width+x,"${value[0]}",1f)
                }
                this.keys=keys.map { it.map { key->key!! }.toList() }.toList().transpose()
            }
            else -> println("unknown cmd")
        }
    }

    fun closePort(){
        if(port?.isOpened == true)port?.closePort()
    }
}