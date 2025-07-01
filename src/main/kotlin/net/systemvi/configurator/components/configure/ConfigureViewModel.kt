package net.systemvi.configurator.components.configure

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import arrow.core.right
import arrow.core.toOption
import net.systemvi.configurator.data.allKeys
import net.systemvi.configurator.data.alphabetKeys
import net.systemvi.configurator.data.fKeys
import net.systemvi.configurator.data.mediaKeys
import net.systemvi.configurator.data.miscKeys
import net.systemvi.configurator.data.modifierKeys
import net.systemvi.configurator.data.numberKeys
import net.systemvi.configurator.data.numpadKeys
import net.systemvi.configurator.data.symbolKeys
import net.systemvi.configurator.model.*
import net.systemvi.configurator.utils.KeyboardSerialApi

fun <T>List<List<T>>.transpose(): List<List<T>> {
    return (this[0].indices).map { i -> (this.indices).map { j -> this[j][i] } }
}

data class KeycapPosition(val x:Int,val y:Int)

enum class KeyboardLayoutPages(val title:String){
    Keymap("Keymap"),
    SaveAndLoad("Save and Load");
}

enum class KeyboardKeysPages(val title:String,val keys:List<Key>){
    All("All", allKeys),
    Alphabet("Alphabet", alphabetKeys),
    Numbers("Numbers", numberKeys),
    Symbols("Symbols", symbolKeys),
    FKeys("Function Keys", fKeys),
    NumpadKeys("Numpad Keys", numpadKeys),
    ModifierKeys("Modifier Keys", modifierKeys),
    MiscKeys("Misc", miscKeys),
    MediaKeys("Media Keys", mediaKeys),
}

class ConfigureViewModel(): ViewModel() {
    var savedKeymaps by mutableStateOf<List<KeyMap>>(listOf(
//        placeholderKeymap("keymap 1"),
//        placeholderKeymap("keymap 2"),
//        placeholderKeymap("keymap 3"),
    ))
    var keymap by mutableStateOf<KeyMap?>(null)
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
            keymapSave(this.keymap!!)
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

    fun keymapLoad(keymap: KeyMap){
        if(this.keymap!=null){
            savedKeymaps=savedKeymaps.map {
                if(it.name==this.keymap!!.name)
                    this.keymap!!
                else it
            }
        }
        this.keymap=keymap
    }

    fun keymapSave(keymap: KeyMap){
        if(this.keymap!=null){
            savedKeymaps=savedKeymaps.map {
                if(it.name==this.keymap!!.name)
                    this.keymap!!
                else it
            }
        }
    }

    fun keymapSaveAs(keymap: KeyMap){
        savedKeymaps.find { it==keymap }.toOption().onNone {
            savedKeymaps+=keymap
        }
    }
}