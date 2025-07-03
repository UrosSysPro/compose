package net.systemvi.configurator.components.configure

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import arrow.core.getOrElse
import arrow.core.serialization.ArrowModule
import arrow.core.toOption
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json
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
import java.io.File
import java.io.FileWriter
import java.util.Scanner

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
    var savedKeymaps by mutableStateOf(keymapLoadFromDisk())
    var keymap by mutableStateOf<KeyMap?>(null)
    val serialApi=KeyboardSerialApi()
    var currentlyPressedKeycaps:Set<KeycapMatrixPosition> by mutableStateOf(emptySet())
    var currentKeyboardLayoutPage: KeyboardLayoutPages by mutableStateOf(KeyboardLayoutPages.Keymap)
    var currentKeyboardKeysPage: KeyboardKeysPages by mutableStateOf(KeyboardKeysPages.All)

    fun setKeyboardLayoutPage(page: KeyboardLayoutPages){
        currentKeyboardLayoutPage=page
    }

    fun setKeyboardKeysPage(page: KeyboardKeysPages){
        currentKeyboardKeysPage=page
    }

    private var selectedLayer by mutableStateOf(0)
    private var selectedKeycapPositon by mutableStateOf<KeycapPosition?>(null)

    fun isKeycapSelected(i:Int,j:Int):Boolean=
        selectedKeycapPositon!=null
                && selectedKeycapPositon!!.x == i
                && selectedKeycapPositon!!.y == j

    fun selectKeycap(i:Int,j:Int){
        selectedKeycapPositon = KeycapPosition(i,j)
    }

    fun unselectKeycap(){
        selectedKeycapPositon = null
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
            uploadKeycap(keymap,selectedKeycapPositon!!,layer)
            keymapSave(this.keymap!!)
        }
    }

    fun uploadKeycap(keymap:KeyMap,position: KeycapPosition,layer:Int){
        serialApi.uploadKeycap(
            keymap.keycaps[position.x][position.y],
            keymap.keycaps[position.x][position.y].layers[layer].getOrElse { allKeys.last()},
            selectedLayer
        )
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
        if(this.keymap!=null)keymapSave(this.keymap!!)
        this.keymap=keymap
        unselectKeycap()
        setKeyboardLayoutPage(KeyboardLayoutPages.Keymap)
    }

    fun keymapSave(keymap: KeyMap) {
        savedKeymaps = savedKeymaps.map {
            if (it.name == keymap.name) keymap
            else it
        }
        keymapSaveToDisk()
    }

    fun keymapSaveAs(keymap: KeyMap){
        savedKeymaps.find { it==keymap }.toOption()
            .onSome {
                println("[ERROR] keymap with same name exists, nothing is saved")
            }
            .onNone {
                this.keymap = keymap
                savedKeymaps+=keymap
            }
    }

    fun keymapSaveToDisk(){
        val format=Json { serializersModule = ArrowModule }
        val json=format.encodeToString(savedKeymaps)
        val fileWriter= FileWriter("keymaps.json")
        fileWriter.write(json)
        fileWriter.close()
    }
    fun keymapLoadFromDisk():List<KeyMap>{
        val file= File("keymaps.json")
        val format=Json { serializersModule = ArrowModule }
        try {
            val builder= StringBuilder()
            val scanner= Scanner(file)
            while (scanner.hasNextLine())builder.append(scanner.nextLine())
            return format.decodeFromString<List<KeyMap>>(builder.toString())
        }catch (e:Exception){
            return emptyList()
        }
    }

    suspend fun keymapUpload(keymap: KeyMap){
        keymap.keycaps.forEachIndexed { i,row->
            row.forEachIndexed { j,keycap->
                keycap.layers.indices.forEach{ layer ->
                    uploadKeycap(keymap, KeycapPosition(i,j),layer)
                    delay(20)
                }
            }
        }
    }
}