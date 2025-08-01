package net.systemvi.configurator.components.neo_configure

import androidx.compose.runtime.Composable
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
import net.systemvi.configurator.model.KeymapType
import net.systemvi.configurator.model.LayerKeyPosition
import net.systemvi.configurator.model.Macro
import net.systemvi.configurator.model.addLayerKey
import net.systemvi.configurator.model.changeName
import net.systemvi.configurator.model.changeType
import net.systemvi.configurator.model.updateKeycap
import net.systemvi.configurator.utils.api.KeyboardSerialApi
import net.systemvi.configurator.utils.api.KeymapApi
import net.systemvi.configurator.utils.syntax.paired
import net.systemvi.configurator.utils.syntax.tripled
import javax.swing.text.Keymap
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

    fun onStart(keymapApi:KeymapApi,serialApi: KeyboardSerialApi) {
        this.keymapApi = keymapApi.some()
        this.serialApi = serialApi.some()
        println("[INFO] NeoConfigureViewModel onStart() called")
    }

    fun onStop(){
        println("[INFO] NeoConfigureViewModel onStop() called")
    }

    private suspend fun readKeymap(scope: CoroutineScope,serialApi: KeyboardSerialApi):Option<KeyMap> = try {
        suspendCancellableCoroutine{ continuation ->
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

    private suspend fun readName(scope: CoroutineScope,serialApi: KeyboardSerialApi):Option<String> = try{
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

    suspend fun selectPort(scope:CoroutineScope,name:Option<String>){
        Pair(serialApi,name).paired().onSome { (serialApi, name) ->
            serialApi.selectPort(name)

            val keymap = readKeymap(scope,serialApi)

            val name:Option<String> = readName(scope,serialApi)

            //set event listener for keypress on serial port
            serialApi.onKeycapPress { keycap->
                currentlyPressedKeycaps += keycap
            }
            serialApi.onKeycapRelease { keycap->
                currentlyPressedKeycaps -= keycap
            }
            serialApi.enableKeyPressEvents()

            Pair(keymap,name).paired().onSome { (keymap, name) ->
                val keymap=keymap
                    .changeName(name)
                    .changeType(KeymapType.Onboard(false))
                onboardKeymaps = listOf(keymap)
                this.keymap.onNone { openKeymap(keymap) }
            }.onNone {
                println("could not read keymap and name")
                onboardKeymaps = emptyList()
                this.keymap = None
            }
        }
        name.onNone {
            serialApi.onSome { it.closePort() }
            onboardKeymaps = emptyList()
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
            when(keymap.type){
                is KeymapType.Saved -> saveKeymap()
                is KeymapType.Onboard -> updateOnboardKeymaps()
                else -> Unit
            }
        }
    }

    fun setMacro(key: Macro){
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
            when(keymap.type){
                is KeymapType.Saved -> saveKeymap()
                is KeymapType.Onboard -> updateOnboardKeymaps()
                else -> Unit
            }
        }
    }

    fun setLayerKey(layer:Int){
        Pair(keymap,serialApi).paired().onSome { (keymap,serialApi) ->
            currentlySelectedKeycaps.forEach { position->
                val keycap=keymap.keycaps[position.column][position.row]

                this.keymap = this.keymap.map { it.addLayerKey(LayerKeyPosition(keycap.matrixPosition,layer))}
                serialApi.addLayerKeyPosition(keycap.matrixPosition,layer)
            }
            when(keymap.type){
                is KeymapType.Saved -> saveKeymap()
                is KeymapType.Onboard -> updateOnboardKeymaps()
                else -> Unit
            }
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

    fun updateOnboardKeymaps(){
        keymap.onSome { keymap ->
            if(onboardKeymaps.any{it.name==keymap.name}){
                onboardKeymaps=onboardKeymaps.map {
                    if(it.name==keymap.name)
                        keymap
                    else
                        it
                }
            }else{
                onboardKeymaps+=keymap
            }
        }
    }

    fun createCopyOfKeymap(name:String){
        Pair(keymap,keymapApi).paired().onSome { (keymap,api)->
            val keymap = keymap
                .changeName(name)
                .changeType(KeymapType.Saved)
            api.saveAs(keymap)
            this.keymap=keymap.some()
        }
    }

    var onboardKeymaps by mutableStateOf(emptyList<KeyMap>())

    fun savedKeymaps():List<KeyMap> = keymapApi.map { it.savedKeymaps }.getOrElse { emptyList() }

    fun defaultKeymaps():List<KeyMap> = net.systemvi.configurator.data.defaultKeymaps()

    suspend fun uploadKeymap(onStatusUpdate:(UploadStatus)->Unit){
        Triple(keymap,keymapApi,serialApi).tripled().onSome { (keymap,keymapApi,serialApi) ->
            keymapApi.upload(serialApi,keymap,onStatusUpdate)
            serialApi.storeToFlash()
        }
    }
}
