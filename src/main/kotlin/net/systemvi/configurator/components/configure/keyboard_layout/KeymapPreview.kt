package net.systemvi.configurator.components.configure.keyboard_layout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import net.systemvi.configurator.components.configure.ConfigureViewModel
import net.systemvi.configurator.model.KeyMap

@Composable fun KeymapPreview(keymap: KeyMap){
    val viewModel= viewModel { ConfigureViewModel() }
    Card(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .size(200.dp,150.dp)
            .clickable(onClick = {viewModel.loadKeymap(keymap)})
        ,
    ) {
       Column{
           Text(keymap.name)
           Column{
               keymap.keycaps.forEach { row ->
                   Row{
                        row.forEach { key->
                            //val name=when(key.layers.first()){
                            //    is Either.Left -> "macro"
                            //    is Either.Right -> key.layers.first().getOrNull()!!.name
                            //}
                            //Text(name)
                            val size=14f
                            val padding=4f
                            val width=size*key.width.size
                            val height=size
                            Box(
                                modifier = Modifier
                                    .padding(padding.dp)
                                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(2.dp))
                                    .size(
                                        (width-padding).dp,
                                        (height-padding).dp
                                    )
                            ){}
                        }
                   }
               }
           }
       }
    }
}