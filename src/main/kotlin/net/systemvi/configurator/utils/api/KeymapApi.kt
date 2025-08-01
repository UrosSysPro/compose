package net.systemvi.configurator.utils.api

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import arrow.core.Either
import arrow.core.serialization.ArrowModule
import arrow.core.toOption
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json
import net.systemvi.configurator.components.configure.UploadStatus
import net.systemvi.configurator.model.KeyMap
import net.systemvi.configurator.model.KeymapType
import net.systemvi.configurator.model.Macro
import net.systemvi.configurator.model.changeType
import net.systemvi.configurator.utils.api.KeyboardSerialApi
import java.io.File
import java.io.FileWriter
import java.util.Scanner

class KeymapApi{

    var savedKeymaps: List<KeyMap> by mutableStateOf(emptyList())
//    private set

    fun macroKeys()=emptyList<Macro>()

    fun save(keymap: KeyMap) {
        savedKeymaps = savedKeymaps.map {
            if (it.name == keymap.name) keymap
            else it
        }
    }

    fun saveAs(keymap: KeyMap){
        savedKeymaps.find { it==keymap }.toOption()
            .onSome {
                println("[ERROR] keymap with same name exists, nothing is saved")
            }
            .onNone {
                savedKeymaps+=keymap.changeType(KeymapType.Saved)
            }
    }

    fun saveToDisk(){
        try {
            val format= Json { serializersModule = ArrowModule }
            val json=format.encodeToString(savedKeymaps)
            val fileWriter= FileWriter("keymaps.json")
            fileWriter.write(json)
            fileWriter.close()
            println("[Success]Saved keymaps to disk")
        }catch (e:Exception){
            println("[ERROR]Error saving keymaps to disk")
            e.printStackTrace()
        }
    }

    suspend fun upload(serialApi: KeyboardSerialApi, keymap: KeyMap,onStatusUpdate:(UploadStatus)->Unit={}){
        val total=keymap.keycaps.flatten().size*4+keymap.snapTapPairs.size+keymap.layerKeyPositions.size+1
        var done=0
        onStatusUpdate(UploadStatus.InProgress(done,total))
        serialApi.deleteKeymap()
        done++
        onStatusUpdate(UploadStatus.InProgress(done,total))
        delay(5)

        keymap.keycaps.forEach { row->
            row.forEach { keycap->
                keycap.layers.indices.forEach{ layer ->
                    val key=keycap.layers[layer]
                    when(key){
                        is Either.Left -> serialApi.setKeyOnLayer(key.value,layer,keycap.matrixPosition)
                        is Either.Right -> serialApi.setKeyOnLayer(key.value,layer,keycap.matrixPosition)
                    }
                    done++
                    onStatusUpdate(UploadStatus.InProgress(done,total))
                    delay(5)
                }
            }
        }

        keymap.snapTapPairs.forEach { snapTapPair->
            serialApi.addSnapTapPair(snapTapPair)
            done++
            onStatusUpdate(UploadStatus.InProgress(done,total))
            delay(5)
        }

        keymap.layerKeyPositions.forEach { layerKeyPosition->
            serialApi.addLayerKeyPosition(layerKeyPosition.position,layerKeyPosition.layer)
            done++
            onStatusUpdate(UploadStatus.InProgress(done,total))
            delay(5)
        }

        onStatusUpdate(UploadStatus.Idle)
    }

    fun loadFromDisk():List<KeyMap>{
        val file = File("keymaps.json")
        val format = Json { serializersModule = ArrowModule }
        try {
            val builder= StringBuilder()
            val scanner= Scanner(file)
            while (scanner.hasNextLine())builder.append(scanner.nextLine())
            val keymaps=format.decodeFromString<List<KeyMap>>(builder.toString())
            this.savedKeymaps=keymaps
            println("[Success]keymaps loaded from disk")
            return keymaps
        }catch (e:Exception){
            println("[ERROR] error loading keymaps from disk")
            e.printStackTrace()
            this.savedKeymaps = emptyList()
            return emptyList()
        }
    }
}