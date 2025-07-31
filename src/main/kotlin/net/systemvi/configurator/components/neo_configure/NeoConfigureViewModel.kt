package net.systemvi.configurator.components.neo_configure

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import arrow.core.None
import arrow.core.Option
import arrow.core.some
import net.systemvi.configurator.components.common.keyboard_grid.KeycapParam
import net.systemvi.configurator.model.KeyMap
import net.systemvi.configurator.model.Keycap
import net.systemvi.configurator.model.KeycapMatrixPosition
import net.systemvi.configurator.utils.api.KeyboardSerialApi
import net.systemvi.configurator.utils.api.KeymapApi
import net.systemvi.configurator.utils.syntax.paired

data class SnapTapSelection(
    var isSelecting: MutableState<Boolean>,
    var first: MutableState<Option<KeycapMatrixPosition>>,
    var second: MutableState<Option<KeycapMatrixPosition>>,
){
    companion object{
        fun create():SnapTapSelection{
            return SnapTapSelection(
                mutableStateOf(false),
                mutableStateOf(None),
                mutableStateOf(None),
            )
        }
    }
}

class NeoConfigureViewModel: ViewModel() {
    var serialApi: Option<KeyboardSerialApi> =   None
    var keymapApi: Option<KeymapApi>         =   None
    var keymap                               by  mutableStateOf<Option<KeyMap>>(None)
    val snapTapSelection                     =   SnapTapSelection.create()
    var currentlyPressedKeycaps              by  mutableStateOf(emptySet<KeycapMatrixPosition>())
    var currentlySelectedKeycaps             by  mutableStateOf(emptySet<KeycapMatrixPosition>())
    var currentLayer                         by  mutableStateOf(0)

    fun onStart(keymapApi:KeymapApi,serialApi: KeyboardSerialApi) {
        this.keymapApi = keymapApi.some()
        this.serialApi = serialApi.some()
        println("[INFO] NeoConfigureViewModel onStart() called")
    }

    fun onStop(){
        println("[INFO] NeoConfigureViewModel onStop() called")
    }

    fun selectPort(name:Option<String>){
        Pair(serialApi,name).paired().onSome { (serialApi, name) ->
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
            serialApi.onNameRead { name->
                println(name)
            }
            serialApi.enableKeyPressEvents()
            serialApi.requestKeymapRead()
            serialApi.requestName()
        }
        name.onNone {
            serialApi.onSome { it.closePort() }
            this.keymap = None
        }
    }

    fun keycapClick(keycap: Keycap,ctrlPressed:Boolean){
        if(currentlySelectedKeycaps.contains(keycap.matrixPosition)){
            if(ctrlPressed)
                currentlySelectedKeycaps-=keycap.matrixPosition
            else
                currentlySelectedKeycaps=emptySet()
        }else{
            if(ctrlPressed)
                currentlySelectedKeycaps+=keycap.matrixPosition
            else
                currentlySelectedKeycaps=setOf(keycap.matrixPosition)
        }
    }

    fun openKeymap(keymap: KeyMap){
        this.keymap=keymap.some()
    }
}
