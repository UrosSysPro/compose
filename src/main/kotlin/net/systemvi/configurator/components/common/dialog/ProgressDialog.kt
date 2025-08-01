package net.systemvi.configurator.components.common.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import net.systemvi.configurator.components.configure.UploadStatus

@Composable
fun ProgressDialog(show:Boolean,title:String,progress:Float){
    if(show)Dialog(onDismissRequest = {}){
        Card{
            Column(
                modifier = Modifier
                    .padding(20.dp)
            ){
                Text(
                    text = title,
                    modifier = Modifier.padding(bottom = 20.dp)
                )
                LinearProgressIndicator(
                    progress = { progress },
                )
            }
        }
    }
}