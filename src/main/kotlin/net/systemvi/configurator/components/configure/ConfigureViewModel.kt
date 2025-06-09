package net.systemvi.configurator.components.configure

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import arrow.core.right
import jssc.SerialPort.*
import jssc.*
import net.systemvi.configurator.model.*

fun <T>List<List<T>>.transpose(): List<List<T>> {
    return (this[0].indices).map { i -> (this.indices).map { j -> this[j][i] } }
}

data class SelectedKeycapPositon(val x:Int,val y:Int)

class ConfigureViewModel(): ViewModel() {

    var serialPortNames by mutableStateOf(listOf<String>())
    var selectedPortName by mutableStateOf<String?>(null)
    var port by mutableStateOf<SerialPort?>(null)
    var messageBuffer=listOf<Byte>()

    var keys by mutableStateOf<KeyMap?>( {
        val row0 = "` 1 2 3 4 5 6 7 8 9 0 - = Back"
        val row1 = "Tab q w e r t y u i o p [ ] \\"
        val row2 = "Caps a s d f g h j k l ; ' Enter"
        val row3 = "Shift z x c v b n m , . / Shift"
        val row4 = "Ctrl Win Alt Space Fn Win Alt Ctrl"
        val rows = listOf(row0, row1, row2, row3, row4)

        var keymap = KeyMap( rows.zip(rows.indices).map { (row, j) ->
            row.split(" ").zip(row.split(" ").indices).map { (key, i) ->
                Keycap(listOf(
                    Key(key[0].code.toByte(),key).right()
                ))
            }
        })
        keymap
            .setKeyWidth(0,keymap.keycaps[0].size-1, KeycapWidth.SIZE_2U)   //backspace

            .setKeyWidth(1,0, KeycapWidth.SIZE_15U)                        //tab
            .setKeyWidth(1,keymap.keycaps[1].size-1, KeycapWidth.SIZE_15U) //backslash

            .setKeyWidth(2,0, KeycapWidth.SIZE_175U)                        //caps lock
            .setKeyWidth(2,keymap.keycaps[2].size-1, KeycapWidth.SIZE_225U) //enter

            .setKeyWidth(3,0, KeycapWidth.SIZE_225U)                        //left shift
            .setKeyWidth(3,keymap.keycaps[3].size-1, KeycapWidth.SIZE_275U) //right shift

            .setKeyWidth(4,0, KeycapWidth.SIZE_125U)                        //left ctrl
            .setKeyWidth(4,1, KeycapWidth.SIZE_125U)                        //left win
            .setKeyWidth(4,2, KeycapWidth.SIZE_125U)                        //left alt
            .setKeyWidth(4,3, KeycapWidth.SIZE_625U)                        //space
            .setKeyWidth(4,4, KeycapWidth.SIZE_125U)                        //fn
            .setKeyWidth(4,5, KeycapWidth.SIZE_125U)                        //right alt
            .setKeyWidth(4,6, KeycapWidth.SIZE_125U)                        //right win
            .setKeyWidth(4,7, KeycapWidth.SIZE_125U)                        //right ctrl
    }())

    private var selectedKeycapPositon by mutableStateOf<SelectedKeycapPositon?>(null)

    fun isKeycapSelected(i:Int,j:Int):Boolean=
        selectedKeycapPositon!=null
                && selectedKeycapPositon!!.x == i
                && selectedKeycapPositon!!.y == j

    fun selectKeycap(i:Int,j:Int){
        selectedKeycapPositon = SelectedKeycapPositon(i,j)
    }

    fun setKeyValue(keyId:Int,value:String){
//        keys=keys.map { it.map { key->if (key.id==keyId) ConfiguratorKey(keyId,value,key.size) else key } }
//        for(row in keys){
//            for(key in row){
//                if(key.id==keyId){
//                    selectedKey=key
//                }
//            }
//        }
//        keys.forEachIndexed { i, row ->
//           row.forEachIndexed { j, key ->
//               if(port?.isOpened == true){
//                   if(keyId==key.id){
//                       val bytes: ByteArray = arrayOf(
//                           'k'.code.toByte(),
//                           ('0'.code+j).toByte(),
//                           ('0'.code+i).toByte(),
//                           value[0].code.toByte(),
//                           0,0,0,
//                       ).toByteArray()
//                       port?.writeBytes(bytes)
//                   }
//               }
//           }
//        }
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
            keys=null
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
//        val cmd = buffer[0].toInt().toChar()
//        when(cmd){
//            'l'->{
//                val width=buffer[1].toInt()
//                val height=buffer[2].toInt()
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
//            }
//            else -> println("unknown cmd")
//        }
    }

    fun closePort(){
        if(port?.isOpened == true)port?.closePort()
    }
}