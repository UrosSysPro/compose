package net.systemvi.configurator.components.tester.keycaps

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import arrow.core.getOrElse
import net.systemvi.configurator.components.common.keyboard_grid.KeycapComponent
import net.systemvi.configurator.components.common.keyboard_grid.KeycapParam
import net.systemvi.configurator.components.common.keycaps.FlatKeycap
import net.systemvi.configurator.components.tester.TesterPageViewModel
import net.systemvi.configurator.data.allKeys

val FlatKeycap: KeycapComponent = @Composable {param: KeycapParam ->

    val viewModel = viewModel { TesterPageViewModel() }
    val key = param.keycap.layers[0].getOrElse { allKeys.last()}
    val currentlyClicked = viewModel.currentlyDownKeys.contains(key)
    val wasClicked = viewModel.wasDownKeys.contains(key)

    viewModel.noteEffect(currentlyClicked, param.position.row + param.position.column * 12+24)

    FlatKeycap(currentlyClicked,wasClicked,key.name)
}

val FlatKeycapName = @Composable {
    Text(
        "Flat Keycap",
        modifier = Modifier
            .border(
                BorderStroke(2.dp, MaterialTheme.colorScheme.secondary),
                shape = RoundedCornerShape(10.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp)
    )
}

