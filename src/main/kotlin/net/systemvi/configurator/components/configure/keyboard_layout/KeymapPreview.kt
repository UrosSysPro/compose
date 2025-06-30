package net.systemvi.configurator.components.configure.keyboard_layout

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import net.systemvi.configurator.components.configure.ConfigureViewModel
import net.systemvi.configurator.components.tester.Grid2
import net.systemvi.configurator.model.KeyMap

val keycap=@Composable { key: String, wasDown: Boolean, isDown: Boolean ->
    Box(
        modifier = Modifier.fillMaxSize().padding(2.dp).background(MaterialTheme.colorScheme.primary)
    ){}
}

@Composable fun KeymapPreview(keymap: KeyMap){
    val viewModel= viewModel { ConfigureViewModel() }
    Card(
        modifier = Modifier
            .clipToBounds()
            .padding(end = 10.dp)
            .size(300.dp,150.dp)
            .clickable(onClick = {viewModel.loadKeymap(keymap)})
        ,
    ) {
       Column(
           verticalArrangement = Arrangement.SpaceBetween,
           horizontalAlignment = Alignment.CenterHorizontally,
           modifier = Modifier.fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 20.dp)
       ){
           Text(keymap.name,modifier = Modifier.fillMaxWidth())
           Grid2(keymap,keycap,10)
       }
    }
}