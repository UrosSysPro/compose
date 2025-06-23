package net.systemvi.configurator.components.configure

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import arrow.core.right
import net.systemvi.configurator.model.*
import net.systemvi.configurator.utils.KeyboardSerialApi

fun <T>List<List<T>>.transpose(): List<List<T>> {
    return (this[0].indices).map { i -> (this.indices).map { j -> this[j][i] } }
}

data class KeycapPosition(val x:Int,val y:Int)

class ConfigureViewModel(): ViewModel() {
    var savedKeymaps by mutableStateOf<List<KeyMap>>(listOf(
        placeholderKeymap(),
        placeholderKeymap(),
        placeholderKeymap(),
    ))
    var keymap by mutableStateOf<KeyMap?>(placeholderKeymap())
    val serialApi=KeyboardSerialApi()
    var currentlyPressedKeycaps:Set<KeycapMatrixPosition> by mutableStateOf(emptySet())

    private var selectedLayer by mutableStateOf(0)
    private var selectedKeycapPositon by mutableStateOf<KeycapPosition?>(null)

    fun isKeycapSelected(i:Int,j:Int):Boolean=
        selectedKeycapPositon!=null
                && selectedKeycapPositon!!.x == i
                && selectedKeycapPositon!!.y == j

    fun selectKeycap(i:Int,j:Int){
        selectedKeycapPositon = KeycapPosition(i,j)
    }

    fun selectLayer(layer:Int){
        selectedLayer = layer
    }
    fun selectedLayer()=selectedLayer

    fun setKeyValue(key: Key){
        if(keymap!=null && selectedKeycapPositon!=null){
            val x=selectedKeycapPositon!!.x
            val y=selectedKeycapPositon!!.y
            val keymap=this.keymap!!
            val layer=selectedLayer
            this.keymap=keymap.updateKeycap(x,y,layer,key)
            serialApi.uploadKeycap(
                keymap!!.keycaps[selectedKeycapPositon!!.x][selectedKeycapPositon!!.y],
                key,
                selectedLayer
            )
        }
    }

    fun readPortNames() = serialApi.getPortNames()

    fun selectPort(name:String?){
        serialApi.selectPort(name)
        serialApi.onKeymapRead { keymap->
            println("new keymap read")
            this.keymap = keymap
        }
        serialApi.onKeycapPress { keycap->
           currentlyPressedKeycaps += keycap
        }
        serialApi.onKeycapRelease { keycap->
            currentlyPressedKeycaps -= keycap
        }
        serialApi.enableKeyPressEvents()
        serialApi.requestKeymapRead()
    }

    fun closePort(){
        serialApi.closePort()
    }

    fun placeholderKeymap(): KeyMap{
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
        return keymap
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
    }

    fun loadKeymap(keymap: KeyMap){
        this.keymap=keymap
    }
}