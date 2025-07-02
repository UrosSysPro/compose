package net.systemvi.configurator.components.configure.keyboard_layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import net.systemvi.configurator.components.configure.ConfigureViewModel
import net.systemvi.configurator.components.configure.serial.SerialPortSelector

@Composable fun UploadKeymapButton(){
    val viewModel = viewModel{ ConfigureViewModel() }
    val scope=rememberCoroutineScope()
    OutlinedButton(
        onClick = {scope.launch { viewModel.keymapUpload(viewModel.keymap!!)}},
        modifier = Modifier.padding(end = 16.dp),
    ){
        Text("Upload")
    }
}
@Composable fun SaveAsButton(){
    val viewModel = viewModel{ ConfigureViewModel() }
    OutlinedButton(
        onClick = {println("save as")},
        modifier = Modifier.padding(end = 16.dp),
    ){
        Text("Save As")
    }
}
@Composable fun SaveButton(){
    val viewModel = viewModel{ ConfigureViewModel() }
    OutlinedButton(
        onClick = {viewModel.keymapSave(viewModel.keymap!!)},
        modifier = Modifier.padding(end = 16.dp),
    ){
        Text("Save")
    }
}

@Composable
fun LayerSelector(configureViewModel: ConfigureViewModel = viewModel { ConfigureViewModel() }){
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
            for(layer in 0..3){
                ElevatedButton(
                    onClick = { configureViewModel.selectLayer(layer) },
                    Modifier
                        .width(IntrinsicSize.Min)
                        .height(IntrinsicSize.Min)
                        .padding(vertical=2.dp,horizontal=4.dp),
                    colors= ButtonDefaults.elevatedButtonColors(
                        containerColor = if(layer==configureViewModel.selectedLayer())
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.primaryContainer
                        ,
                        contentColor = if(layer==configureViewModel.selectedLayer())
                            MaterialTheme.colorScheme.primaryContainer
                        else
                            MaterialTheme.colorScheme.primary
                        ,
                    )
                ){
                    Text("${layer+1}")
                }
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(IntrinsicSize.Min)
        ){
            UploadKeymapButton()
            SaveButton()
            SaveAsButton()
            SerialPortSelector()
        }
    }
}