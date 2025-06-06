package net.systemvi.configurator.components.configure.keyboard_layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import net.systemvi.configurator.components.configure.ConfigureViewModel
import net.systemvi.configurator.components.serial.SerialPortSelector

@Composable
fun LayerSelector(configureViewModel: ConfigureViewModel = viewModel { ConfigureViewModel() }){
    data class LayerLink(val name:String,val onClick:()->Unit={})
    val layers=listOf(
        LayerLink("1"),
        LayerLink("2"),
        LayerLink("3"),
        LayerLink("4"),
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp, horizontal = 16.dp)
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
        ) {
            Text("Layer",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 24.sp,
                modifier = Modifier.padding(0.dp,0.dp,30.dp,0.dp)
//            fontSize = 20.dp
            )
            for(layer in layers){
                ElevatedButton(
                    onClick = layer.onClick,
                    Modifier
                        .width(IntrinsicSize.Min)
                        .height(IntrinsicSize.Min)
                        .padding(vertical=2.dp,horizontal=4.dp)
                ){
                    Text(layer.name)
                }
            }
        }
        SerialPortSelector()
    }
}