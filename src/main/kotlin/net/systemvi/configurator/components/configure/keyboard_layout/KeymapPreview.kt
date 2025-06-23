package net.systemvi.configurator.components.configure.keyboard_layout

import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import arrow.core.Either
import net.systemvi.configurator.components.configure.ConfigureViewModel
import net.systemvi.configurator.model.KeyMap

@Composable fun KeymapPreview(keymap: KeyMap){
    val viewModel= viewModel { ConfigureViewModel() }
    Card(
        modifier = Modifier
            .clickable(onClick = {viewModel.loadKeymap(keymap)})
    ) {
       Column{
           Text("Keymap name")
           Column{
               keymap.keycaps.forEach { row ->
                   Row{
                        row.forEach { key->
                            val name=when(key.layers.first()){
                                is Either.Left -> "macro"
                                is Either.Right -> key.layers.first().getOrNull()!!.name
                            }
                            Text(name)
                        }
                   }
               }
           }
       }
    }
}