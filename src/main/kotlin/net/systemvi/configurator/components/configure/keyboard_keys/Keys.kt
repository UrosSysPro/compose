package net.systemvi.configurator.components.configure.keyboard_keys

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
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
import net.systemvi.configurator.model.Key
import net.systemvi.configurator.model.Keycap


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
                configuratorViewModel.setKeyValue(key)
            })
    ){
        Text(
            key.name,
            textAlign = TextAlign.Center
        )
    }
}

@Composable fun Keys(keys:List<Key>) {
    FlowRow (
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
    ) {
       keys.forEach {
           Keycap(it)
       }
    }
}