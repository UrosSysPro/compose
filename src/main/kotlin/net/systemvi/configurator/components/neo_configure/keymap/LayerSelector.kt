package net.systemvi.configurator.components.neo_configure.keymap

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import net.systemvi.configurator.components.neo_configure.NeoConfigureViewModel

@Composable
fun LayerSelector() {
    val neoConfigureViewModel= viewModel { NeoConfigureViewModel() }
    Row (
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ){
        for(i in 0 until 4){
            val selected=i==neoConfigureViewModel.currentLayer
            val background=if(selected) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.onSecondaryContainer
            val text=if(selected) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.secondaryContainer

            ElevatedButton(
                onClick = {neoConfigureViewModel.currentLayer=i},
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = background,
                    contentColor = text
                )
            ){
                Text("${i+1}")
            }
        }
    }
}