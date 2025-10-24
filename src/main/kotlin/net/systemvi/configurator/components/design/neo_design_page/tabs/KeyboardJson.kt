package net.systemvi.configurator.components.design.neo_design_page.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import arrow.core.right
import net.systemvi.configurator.components.design.DesignPageViewModel

@Composable
fun KeyboardJson() {
    val viewModel = viewModel { DesignPageViewModel() }
    val keymap = viewModel.keymap

    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.primary)
    ) {
        keymap.keycaps.forEachIndexed { index, row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Card() {
                    Text(text = "$index")
                }
                row.forEach { keycap ->
                    //OVO POSTOJI NA KEYCAP-U
                    //PITANJE JE STA JE BITNO DA SE PRIKAZE
                    Column {
                        Text("${keycap.layers.right()}")
                        Text("${keycap.matrixPosition}")
                        Text("${keycap.height}")
                        Text("${keycap.width}")
                        Text("${keycap.padding}")
                        Text("${keycap.offset}")
                        Text("${keycap.rotation}")
                    }
                }
            }
        }
    }
}