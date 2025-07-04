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
    keycap: Keycap,
    size1U:Float=60f,
    selected:Boolean,
    onClick:()->Unit,
    pressed:Boolean=false,
    configureViewModel: ConfigureViewModel=viewModel{ConfigureViewModel()},
){
    val layer=configureViewModel.selectedLayer().coerceAtMost(keycap.layers.size-1)
    val backgroundColor=when{
        pressed-> MaterialTheme.colorScheme.tertiaryContainer
        selected->MaterialTheme.colorScheme.primary
        else->MaterialTheme.colorScheme.primaryContainer
    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .size((size1U*keycap.width.size).dp,50.dp)
            .padding(2.dp)
            .clip(RoundedCornerShape(10.dp))
            .combinedClickable(enabled = true, onClick = onClick)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(10.dp)
            )
            .border(
                BorderStroke(2.dp,MaterialTheme.colorScheme.secondary),
                shape = RoundedCornerShape(10.dp)
            )
    ){
        Text(keycap.layers[layer].map { key:Key ->key.name }.getOrElse { "???" })
    }
}

@Composable
fun KeyboardLayoutGridView(
    configureViewModel: ConfigureViewModel=viewModel{ConfigureViewModel()}
) {
    val keymap=configureViewModel.keymapApi.keymap
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .horizontalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column{
            keymap.onSome { keymap ->
                keymap.keycaps.forEachIndexed { i,row->
                    Row{
                        row.forEachIndexed { j,key->
                            Keycap(
                                key,
                                selected = configureViewModel.isKeycapSelected(i,j),
                                onClick = { configureViewModel.selectKeycap(i,j) },
                                pressed = configureViewModel.currentlyPressedKeycaps.contains(key.matrixPosition),
                            )
                        }
                    }
                }
            }
        }
    }
}