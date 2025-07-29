package net.systemvi.configurator.components.neo_configure

import androidx.compose.runtime.Composable
import net.systemvi.configurator.components.neo_configure.keymap.KeymapGrid
import net.systemvi.configurator.components.neo_configure.keymap.PleaseSelectKeymapOrPort

@Composable
fun NeoConfigKeymap(){
    if(true){
        KeymapGrid()
    }else{
        PleaseSelectKeymapOrPort()
    }
}