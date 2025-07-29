package net.systemvi.configurator.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import net.systemvi.configurator.components.neo_configure.NeoConfigKeySelector
import net.systemvi.configurator.components.neo_configure.NeoConfigKeymap
import net.systemvi.configurator.components.neo_configure.NeoConfigKeymapSelector
import net.systemvi.configurator.components.neo_configure.NeoConfigPortSelector
import net.systemvi.configurator.model.padding

@Composable
fun NeoConfigPage() {
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row (
            modifier = Modifier
                .padding(
                    horizontal = 30.dp,
                    vertical = 15.dp
                )
                .fillMaxWidth()
                .zIndex(2f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top,
        ){
            NeoConfigKeySelector()
            NeoConfigKeymapSelector()
            NeoConfigPortSelector()
        }
        Column (
            modifier = Modifier
                .fillMaxSize()
                .zIndex(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NeoConfigKeymap()
        }
        Box(modifier = Modifier.height(100.dp)){}
    }
}