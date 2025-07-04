package net.systemvi.configurator.utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import arrow.core.None
import arrow.core.Option
import arrow.core.getOrElse
import arrow.core.serialization.ArrowModule
import arrow.core.some
import arrow.core.toOption
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json
import net.systemvi.configurator.components.configure.KeyboardLayoutPages
import net.systemvi.configurator.data.allKeys
import net.systemvi.configurator.model.KeyMap
import java.io.File
import java.io.FileWriter
import java.util.Scanner
import kotlin.collections.plus

class KeymapApi{

    var savedKeymaps by mutableStateOf(loadFromDisk())
    var keymap by mutableStateOf<Option<KeyMap>>(None)
    val passKey=allKeys.find { it.value==0.toByte() }!!

    fun keymapLoad(keymap: KeyMap){
        this.keymap.onSome { save(it) }
        this.keymap=keymap.some()
//        unselectKeycap()
//        setKeyboardLayoutPage(KeyboardLayoutPages.Keymap)
    }

    fun save(keymap: KeyMap) {
        savedKeymaps = savedKeymaps.map {
            if (it.name == keymap.name) keymap
            else it
        }
        saveToDisk()
    }

    fun saveAs(keymap: KeyMap){
        savedKeymaps.find { it==keymap }.toOption()
            .onSome {
                println("[ERROR] keymap with same name exists, nothing is saved")
            }
            .onNone {
                this.keymap = keymap.some()
                savedKeymaps+=keymap
            }
        saveToDisk()
    }

    fun saveToDisk(){
        try {
            val format=Json { serializersModule = ArrowModule }
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

    suspend fun upload(serialApi: KeyboardSerialApi, keymap: KeyMap){
        keymap.keycaps.forEach { row->
            row.forEach { keycap->
                keycap.layers.indices.forEach{ layer ->
                    val key=keycap.layers[layer].getOrElse { passKey }
                    serialApi.setKeyOnLayer(key,layer,keycap.matrixPosition)
                    delay(20)
                }
            }
        }
    }

    fun loadFromDisk():List<KeyMap>{
        val file= File("keymaps.json")
        val format=Json { serializersModule = ArrowModule }
        try {
            val builder= StringBuilder()
            val scanner= Scanner(file)
            while (scanner.hasNextLine())builder.append(scanner.nextLine())
            val keymaps=format.decodeFromString<List<KeyMap>>(builder.toString())
            println("[Success]keymaps loaded from disk")
            return keymaps
        }catch (e:Exception){
            println("[ERROR] error loading keymaps from disk")
            e.printStackTrace()
            return emptyList()
        }
    }
}