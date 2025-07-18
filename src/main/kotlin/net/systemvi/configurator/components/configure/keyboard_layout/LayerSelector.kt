package net.systemvi.configurator.components.configure.keyboard_layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import net.systemvi.configurator.components.configure.ConfigureViewModel
import net.systemvi.configurator.components.configure.UploadStatus
import net.systemvi.configurator.components.configure.serial.SerialPortSelector
import net.systemvi.configurator.model.exportStl
import kotlin.math.tan

@Composable fun UploadKeymapButton(){
    val viewModel = viewModel{ ConfigureViewModel() }
    val scope=rememberCoroutineScope()
    var uploadStatus: UploadStatus by remember { mutableStateOf(UploadStatus.Idle)}
    viewModel.keymap.onSome { keymap ->
        OutlinedButton(
            onClick = {scope.launch {
                viewModel.keymapUpload(
                    keymap = keymap,
                    onStatusUpdate = { uploadStatus=it }
                )
            }},
            modifier = Modifier.padding(end = 16.dp),
        ){
            Text("Upload")
        }
    }
    when(uploadStatus){
        is UploadStatus.InProgress -> {
            val status=uploadStatus as UploadStatus.InProgress
            Dialog(onDismissRequest = {}){
                Card{
                    Column(
                        modifier = Modifier
                            .padding(20.dp)
                    ){
                        Text(
                            text = "Uploading...",
                            modifier = Modifier.padding(bottom = 20.dp)
                        )
                        LinearProgressIndicator(
                            progress = { status.done.toFloat()/status.of}
                        )
                    }
                }
            }
        }
        else -> {}
    }
}
@Composable fun SaveAsButton(){
    var openDialog by remember { mutableStateOf(false) }
    OutlinedButton(
        onClick = {openDialog=true},
        modifier = Modifier.padding(end = 16.dp),
    ){
        Text("Save As")
    }
    if(openDialog) SaveAsDialog { openDialog = false }
}
@Composable fun SaveButton(){
    val viewModel = viewModel{ ConfigureViewModel() }
    viewModel.keymap.onSome { keymap ->
        OutlinedButton(
            onClick = {viewModel.keymapSave(keymap)},
            modifier = Modifier.padding(end = 16.dp),
        ){
            Text("Save")
        }
    }
}

@Composable
fun ExportButton(){
    val viewModel = viewModel{ ConfigureViewModel() }
    viewModel.keymap.onSome { keymap ->
        OutlinedButton(
            onClick = { keymap.exportStl("test.stl") },
            modifier = Modifier.padding(end = 16.dp),
        ){
            Text("Export")
        }
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
            ExportButton()
            UploadKeymapButton()
            SaveButton()
            SaveAsButton()
            SerialPortSelector()
        }
    }
}