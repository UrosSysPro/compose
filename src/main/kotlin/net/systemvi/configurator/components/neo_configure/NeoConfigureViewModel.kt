package net.systemvi.configurator.components.neo_configure

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import arrow.core.None
import arrow.core.Option
import arrow.core.getOrElse
import arrow.core.some
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import net.systemvi.configurator.components.configure.UploadStatus
import net.systemvi.configurator.model.Key
import net.systemvi.configurator.model.KeyMap
import net.systemvi.configurator.model.KeycapMatrixPosition
import net.systemvi.configurator.model.KeycapPosition
import net.systemvi.configurator.model.changeName
import net.systemvi.configurator.model.updateKeycap
import net.systemvi.configurator.utils.api.KeyboardSerialApi
import net.systemvi.configurator.utils.api.KeymapApi
import net.systemvi.configurator.utils.syntax.paired
import net.systemvi.configurator.utils.syntax.tripled
import kotlin.coroutines.resume

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
    var currentlySelectedKeycaps             by  mutableStateOf(emptySet<KeycapPosition>())
    var currentLayer                         by  mutableStateOf(0)
    var onboardKeymaps                       by  mutableStateOf(emptyList<KeyMap>())

    fun onStart(keymapApi:KeymapApi,serialApi: KeyboardSerialApi) {
        this.keymapApi = keymapApi.some()
        this.serialApi = serialApi.some()
        println("[INFO] NeoConfigureViewModel onStart() called")
    }

    fun onStop(){
        println("[INFO] NeoConfigureViewModel onStop() called")
    }

    suspend fun selectPort(scope:CoroutineScope,name:Option<String>){
        Pair(serialApi,name).paired().onSome { (serialApi, name) ->
            serialApi.selectPort(name)

            val keymap = try {
                suspendCancellableCoroutine<Option<KeyMap>>{ continuation ->
                    val cancellationJob=scope.launch {
                        delay(500)
                        if(continuation.isActive) continuation.cancel()
                    }
                    //read keymap
                    serialApi.onKeymapRead { keymap->
                        cancellationJob.cancel()
                        continuation.resume(keymap.some())
                    }
                    serialApi.requestKeymapRead()
                }
            }catch (_:Exception){
                None
            }

            val name:Option<String> = try{
                suspendCancellableCoroutine { continuation ->
                    //read name
                    val cancellationJob=scope.launch {
                        delay(500)
                        if(continuation.isActive) continuation.cancel()
                    }
                    serialApi.onNameRead { name->
                        cancellationJob.cancel()
                        continuation.resume(name.some())
                    }
                    serialApi.requestName()
                }
            }catch (_:Exception){
//                None
                "Name not found".some()
            }

            //set event listener for keypress on serial port
            serialApi.onKeycapPress { keycap->
                currentlyPressedKeycaps += keycap
            }
            serialApi.onKeycapRelease { keycap->
                currentlyPressedKeycaps -= keycap
            }
            serialApi.enableKeyPressEvents()

            Pair(keymap,name).paired().onSome { (keymap, name) ->
                openKeymap(keymap.changeName(name))
            }.onNone {
                println("could not read keymap and name")
                this.keymap = None
            }
        }
        name.onNone {
            serialApi.onSome { it.closePort() }
            this.keymap = None
        }
    }

    fun keycapClick(position: KeycapPosition,ctrlPressed:Boolean){
        if(currentlySelectedKeycaps.contains(position)){
            if(ctrlPressed)
                currentlySelectedKeycaps-=position
            else
                currentlySelectedKeycaps=emptySet()
        }else{
            if(ctrlPressed)
                currentlySelectedKeycaps+=position
            else
                currentlySelectedKeycaps=setOf(position)
        }
    }

    fun openKeymap(keymap: KeyMap){
        currentLayer=0
        currentlySelectedKeycaps=emptySet()
        currentlyPressedKeycaps=emptySet()
        this.keymap=keymap.some()
    }

    fun setKey(key: Key){
        Pair(keymap,serialApi).paired().onSome { (keymap,serialApi) ->
            currentlySelectedKeycaps.forEach { position->
                this.keymap = this.keymap.map { it.updateKeycap(
                    position.column,
                    position.row,
                    currentLayer,
                    key
                )}

                serialApi.setKeyOnLayer(
                    key,
                    currentLayer,
                    keymap.keycaps[position.column][position.row].matrixPosition
                )
            }
            saveKeymap()
        }
    }

    fun saveKeymap(){
        Pair(keymap,keymapApi).paired().onSome { (keymap,keymapApi) ->
            if(keymapApi.savedKeymaps.any{it.name==keymap.name}){
                keymapApi.savedKeymaps=keymapApi.savedKeymaps.map {
                    if(it.name==keymap.name)
                        keymap
                    else
                        it
                }
            }else{
                keymapApi.savedKeymaps+=keymap
            }
        }
    }

    fun savedKeymaps():List<KeyMap> = keymapApi.map { it.savedKeymaps }.getOrElse { emptyList() }

    fun defaultKeymaps():List<KeyMap> = net.systemvi.configurator.data.defaultKeymaps()

    suspend fun uploadKeymap(onStatusUpdate:(UploadStatus)->Unit){
        Triple(keymap,keymapApi,serialApi).tripled().onSome { (keymap,keymapApi,serialApi) ->
            keymapApi.upload(serialApi,keymap,onStatusUpdate)
            serialApi.storeToFlash()
        }
    }
}
