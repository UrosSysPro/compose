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
import arrow.core.getOrElse
import net.systemvi.configurator.components.configure.ConfigureViewModel
import net.systemvi.configurator.model.Key
import net.systemvi.configurator.model.Keycap

@Composable fun Keycap(
    key: Keycap,
    size1U:Float=50f,
    selected:Boolean,
    onClick:()->Unit,
    configureViewModel: ConfigureViewModel=viewModel{ConfigureViewModel()}
){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .size((size1U*key.width.size).dp,50.dp)
            .padding(2.dp)
            .clip(RoundedCornerShape(10.dp))
            .combinedClickable(enabled = true, onClick = onClick)
            .background(
                color = if(selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(10.dp)
            )
            .border(
                BorderStroke(2.dp,MaterialTheme.colorScheme.secondary),
                shape = RoundedCornerShape(10.dp)
            )
    ){
        Text(key.layers[0].map { key:Key ->key.name }.getOrElse { "???" })
    }
}

@Composable
fun KeyboardLayoutGridView(
    configureViewModel: ConfigureViewModel=viewModel{ConfigureViewModel()}
) {
    val keys=configureViewModel.keymap
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .horizontalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column{
            keys?.keycaps?.forEachIndexed { i,row->
                Row{
                    row.forEachIndexed { j,key->
                        Keycap(
                            key,
                            selected = configureViewModel.isKeycapSelected(i,j),
                            onClick = { configureViewModel.selectKeycap(i,j) }
                        )
                    }
                }
            }
        }
    }
}