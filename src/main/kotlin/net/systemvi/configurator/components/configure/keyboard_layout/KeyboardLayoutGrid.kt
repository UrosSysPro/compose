package net.systemvi.configurator.components.configure.keyboard_layout

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import net.systemvi.configurator.components.configure.ConfigureViewModel


@Composable fun Keycap(
    key:ConfiguratorKey,
    size1U:Float=50f,
    configureViewModel: ConfigureViewModel=viewModel{ConfigureViewModel()}
){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .size((size1U*key.size).dp,50.dp)
            .padding(2.dp)
            .clip(RoundedCornerShape(10.dp))
            .combinedClickable(enabled = true, onClick = {configureViewModel.selectedKey=key})
            .background(
                color = if(configureViewModel.selectedKey==key) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(10.dp)
            )
            .border(
                BorderStroke(2.dp,MaterialTheme.colorScheme.secondary),
                shape = RoundedCornerShape(10.dp)
            )
    ){
        Text(key.value)
    }
}

@Composable
fun KeyboardLayoutGrid(
    configureViewModel: ConfigureViewModel=viewModel{ConfigureViewModel()}
) {
    val keys=configureViewModel.keys
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column{
            keys.forEach { row->
                Row{
                    row.forEach { key->
                        Keycap(key)
                    }
                }
            }
        }
    }
}