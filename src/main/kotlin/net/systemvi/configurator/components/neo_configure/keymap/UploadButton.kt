package net.systemvi.configurator.components.neo_configure.keymap

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import net.systemvi.configurator.components.common.dialog.ProgressDialog
import net.systemvi.configurator.components.configure.UploadStatus
import net.systemvi.configurator.components.neo_configure.NeoConfigureViewModel

@Composable
fun UploadButton(){
    val neoConfigureViewModel= viewModel { NeoConfigureViewModel() }
    val scope = rememberCoroutineScope()
    var status by remember { mutableStateOf<UploadStatus>(UploadStatus.Idle) }

    val progress = when(status){
        is UploadStatus.InProgress -> {
            val status = status as UploadStatus.InProgress
            status.done.toFloat()/ status.of.toFloat()
        }
        else -> 0f
    }

    val show = when(status){
        is UploadStatus.InProgress -> true
        else -> false
    }

    Box(modifier = Modifier.height(IntrinsicSize.Min)) {
        ElevatedButton(
            modifier = Modifier,
            onClick = { scope.launch {
                neoConfigureViewModel.uploadKeymap{ status = it }
            }},
        ){
            Text("Upload")
        }
    }
    ProgressDialog(show,"Uploading...",progress)
}