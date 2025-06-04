package net.systemvi.configurator.components.configure.keyboard_keys

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import net.systemvi.configurator.components.configure.ConfigureViewModel
import net.systemvi.configurator.components.configure.keyboard_layout.ConfiguratorKey

data class Key(val value:String)

@Composable fun Keycap(
    key:Key,
    configuratorViewModel: ConfigureViewModel= viewModel { ConfigureViewModel() },
){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp) // outer padding
            .size(80.dp, 50.dp)
            .border(
                border = BorderStroke(2.dp,MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(8.dp)
            )
            .combinedClickable(onClick = {
                if(configuratorViewModel.selectedKey!=null)
                    configuratorViewModel.setKeyValue(configuratorViewModel.selectedKey!!.id,key.value)
            })
    ){
        Text(
            key.value,
            textAlign = TextAlign.Center
        )
    }
}

@Composable fun Keys(){
    val alphabet=listOf(
        "q w e r t y u i o p",
        "a s d f g h j k l",
        "z x c v b n m",
        "1 2 3 4 5 6 7 8 9 0",
        "F1 F2 F3 F4 F5 F6 F7 F8 F9 F10 F11 F12",
        "Esc ` Tab Caps_Lock L_Shift L_Ctrl L_Win L_Alt",
        "Backspace Enter R_Shift R_Ctrl R_Win R_Alt"
    )
//    alphabet.fold(""){acc,s->acc+s}
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
    ) {
        for(row in alphabet){
            Row{
                for(key in row.split(" ")) Keycap(Key(key))
            }
        }
    }
}