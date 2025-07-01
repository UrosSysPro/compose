package net.systemvi.configurator.components.tester

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.material3.ElevatedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import net.systemvi.configurator.components.common.keycaps.Keycap
import net.systemvi.configurator.data.defaultKeymaps

@Composable
fun ResetButton() {
    val viewModel = viewModel { TesterPageViewModel() }
    Box(
        Modifier.size(250.dp, 50.dp),
        contentAlignment = Alignment.Center
    ) {
        ElevatedButton(
            onClick = { viewModel.resetKeys() },
            modifier = Modifier.fillMaxSize(),
        ) {
            Text("Reset")
        }
    }
}

@Composable fun TesterPage(modifier: Modifier) {
    Column(
        modifier = Modifier.fillMaxSize().then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Grid(defaultKeymaps()[2], Keycap)
        ResetButton()
    }
}