package net.systemvi.configurator.components.configure

import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import arrow.core.None
import arrow.core.Option
import arrow.core.some
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
import net.systemvi.configurator.utils.api.KeyboardSerialApi
import net.systemvi.configurator.utils.api.KeymapApi

data class KeycapPosition(val x:Int,val y:Int)

enum class KeyboardLayoutPages(val title:String){
    Keymap("Keymap"),
    SaveAndLoad("Save and Load");
}

abstract class UploadStatus
class Idle:UploadStatus()
data class InProgress(val done:Int,val of: Int):UploadStatus()

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
    LayerKeys("Layer Keys",emptyList()),
    SnapTapKeys("Snap tap",emptyList()),
    MacroKeys("Macros",emptyList()),
}

data class CurrentlySelectedSnapTapKeys(var first: KeycapMatrixPosition? = null,var second: KeycapMatrixPosition? = null)

class ConfigureViewModel(): ViewModel() {
    lateinit var serialApi:KeyboardSerialApi
    lateinit var keymapApi: KeymapApi
    var keymap by mutableStateOf<Option<KeyMap>>(None)
    var currentlyPressedKeycaps:Set<KeycapMatrixPosition> by mutableStateOf(emptySet())
    var currentKeyboardLayoutPage: KeyboardLayoutPages by mutableStateOf(KeyboardLayoutPages.Keymap)
    var currentKeyboardKeysPage: KeyboardKeysPages by mutableStateOf(KeyboardKeysPages.All)
    var currentlySelectingSnapTapPair by mutableStateOf(false)
    var currentlySelectedSnapTapKeys by mutableStateOf(CurrentlySelectedSnapTapKeys())

    fun onStart(keymapApi: KeymapApi,serialApi: KeyboardSerialApi) {
        this.keymapApi = keymapApi
        this.serialApi = serialApi
        println("configure view model start")
    }

    fun onStop(){
        keymap.onSome { keymap->keymapApi.save(keymap) }
        keymap = None
        println("configure view model stop")
    }

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

    fun setNormalKey(key: Key){
        keymap.onSome { keymap->
            if(selectedKeycapPositon!=null){
                val x=selectedKeycapPositon!!.x
                val y=selectedKeycapPositon!!.y
                val keycap=keymap.keycaps[x][y]
                val layer=selectedLayer
                keymap.layerKeyPositions.find { it.position==keycap.matrixPosition }.toOption().onSome { layerKeyPosition->
                    serialApi.removeLayerKeyPosition(layerKeyPosition.position)
                    this.keymap=this.keymap.map { it.removeLayerKey(layerKeyPosition.position) }
                }
                this.keymap=this.keymap.map { it.updateKeycap(x,y,layer,key) }
                keymapApi.save(this.keymap.getOrNull()!!)
                serialApi.setKeyOnLayer(key,layer,keycap.matrixPosition)
            }
        }
    }

    fun setMacroKey(macro:Macro){
        keymap.onSome { keymap->
            if(selectedKeycapPositon!=null){
                val x=selectedKeycapPositon!!.x
                val y=selectedKeycapPositon!!.y
                val keycap=keymap.keycaps[x][y]
                val layer=selectedLayer
                keymap.layerKeyPositions.find { it.position==keycap.matrixPosition }.toOption().onSome { layerKeyPosition->
                    serialApi.removeLayerKeyPosition(layerKeyPosition.position)
                    this.keymap=this.keymap.map { it.removeLayerKey(layerKeyPosition.position) }
                }
                this.keymap=this.keymap.map { it.updateKeycap(x,y,layer,macro) }
                keymapApi.save(this.keymap.getOrNull()!!)
                serialApi.setKeyOnLayer(macro,layer, keycap.matrixPosition)
            }
        }
    }

    fun setLayerKey(layer:Int){
        keymap.onSome { keymap->
            if(selectedKeycapPositon!=null){
                val x=selectedKeycapPositon!!.x
                val y=selectedKeycapPositon!!.y
                val keycap=keymap.keycaps[x][y]
                this.keymap=keymap.addLayerKey(LayerKeyPosition(keycap.matrixPosition,layer)).some()
                keymapApi.save(this.keymap.getOrNull()!!)
                serialApi.addLayerKeyPosition(keycap.matrixPosition,layer)
            }
        }
    }

    fun setSnapTapPair(pair: SnapTapPair){
        this.keymap.onSome { keymap->
            this.keymap=keymap.addSnapTapPair(pair).some()
            keymapApi.save(this.keymap.getOrNull()!!)
            serialApi.addSnapTapPair(pair)
        }
    }

    fun removeSnapTapPair(pair: SnapTapPair){
        this.keymap.onSome { keymap->
            this.keymap=keymap.removeSnapTapPair(pair).some()
            keymapApi.save(this.keymap.getOrNull()!!)
            serialApi.removeSnapTapPair(pair)
        }
    }

    fun readPortNames() = serialApi.getPortNames()

    fun selectPort(name:String?){
        serialApi.selectPort(name)
        serialApi.onKeymapRead { keymap->
            println("new keymap read")
            this.keymap=keymap.some()
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

    fun keymapLoad(keymap: KeyMap){
        this.keymap= keymap.some()
        unselectKeycap()
        setKeyboardLayoutPage(KeyboardLayoutPages.Keymap)
    }

    fun keymapSave(keymap: KeyMap) {
        keymapApi.save(keymap)
    }

    fun keymapSaveAs(keymap: KeyMap){
        keymapApi.saveAs(keymap)
    }

    suspend fun keymapUpload(keymap: KeyMap,onStatusUpdate:(UploadStatus)->Unit={}){
        keymapApi.upload(serialApi,keymap)
    }
}