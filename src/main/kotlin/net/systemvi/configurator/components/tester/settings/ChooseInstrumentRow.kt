package net.systemvi.configurator.components.tester.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import net.systemvi.configurator.components.tester.TesterPageViewModel

@Composable
fun ChooseInstrumentRow(){
    val viewModel = viewModel { TesterPageViewModel() }
    Row(
        modifier = Modifier.fillMaxWidth().height(75.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Choose Instrument")
        SingleChoiceSegmentedButtonRow {
            viewModel.instruments.forEachIndexed { index, (name, instrument) ->
                SegmentedButton(
                    shape = SegmentedButtonDefaults.itemShape(
                        index = index,
                        count = viewModel.instruments.size
                    ),
                    onClick = {
                        viewModel.selectedInstrument = instrument
                        viewModel.focusRequester.requestFocus()
                    },
                    selected = instrument == viewModel.selectedInstrument,
                    label = {
                        Text(
                            text = name,
                            style = MaterialTheme.typography.bodySmall
                        )
                    },
                    modifier = Modifier.height(50.dp)
                )
            }
        }
    }
}