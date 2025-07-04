package net.systemvi.configurator.components.tester.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import net.systemvi.configurator.components.tester.TesterPageViewModel

@Composable
private fun Settings() {

    val dividerThickness = 3.dp
    val dividerColor = MaterialTheme.colorScheme.primary

    Column(modifier = Modifier.width(900.dp)) {
        ResetRow()
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = dividerColor,
            thickness = dividerThickness
        )
        MuteSoundRow()
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = dividerColor,
            thickness = dividerThickness
        )
        KeycapSelectorRow()
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = dividerColor,
            thickness = dividerThickness
        )
        ChooseInstrumentRow()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetV1() {
    val viewModel = viewModel { TesterPageViewModel() }

    if (viewModel.showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                viewModel.showBottomSheet = false
                viewModel.focusRequester.requestFocus()
            },
        ) {
            Settings()
        }
    }
}
