package net.systemvi.configurator.components.configure

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import net.systemvi.configurator.components.configure.keyboard_layout.ConfiguratorKey

class ConfigureViewModel(): ViewModel() {
    val row0 = "` 1 2 3 4 5 6 7 8 9 0 - = Back"
    val row1 = "Tab q w e r t y u i o p [ ] \\"
    val row2 = "Caps a s d f g h j k l ; ' Enter"
    val row3 = "Shift z x c v b n m , . / Shift"
    val row4 = "Ctrl Win Alt Space Fn Win Alt Ctrl"
    val rows = listOf(row0, row1, row2, row3, row4)

    var keys by mutableStateOf( {
        val keys= rows.zip(rows.indices).map { (row, j) ->
            row.split(" ").zip(row.split(" ").indices).map { (key, i) ->
                ConfiguratorKey(j * 100 + i, key, 1.0f)
            }
        }
        keys[0].last().size=2f

        keys[1][0].size=1.25f // tab
        keys[1].last().size=1.75f // tab

        keys[2][0].size=1.5f // caps
        keys[2].last().size=2.5f // enter

        keys[3][0].size=2f  // left shift
        keys[3].last().size=3f  // left shift

        keys[4][0].size=1.25f //ctrl
        keys[4][1].size=1.25f //win
        keys[4][2].size=1.25f //alt
        keys[4][3].size=6.25f //space
        keys[4][4].size=1.25f //fn
        keys[4][5].size=1.25f //win
        keys[4][6].size=1.25f //alt
        keys[4][7].size=1.25f //ctrl
        keys
    }())

    var selectedKey by mutableStateOf<ConfiguratorKey?>(null)

    fun setKeyValue(keyId:Int,value:String){
        keys=keys.map { it.map { key->if (key.id==keyId) ConfiguratorKey(keyId,value,key.size) else key } }
        for(row in keys){
            for(key in row){
                if(key.id==keyId){
                    selectedKey=key
                }
            }
        }
    }
}