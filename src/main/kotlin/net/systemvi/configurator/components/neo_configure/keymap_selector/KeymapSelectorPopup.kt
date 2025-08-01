package net.systemvi.configurator.components.neo_configure.keymap_selector

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import arrow.core.getOrElse
import net.systemvi.configurator.components.neo_configure.NeoConfigureViewModel
import net.systemvi.configurator.data.defaultKeymaps

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun KeymapsPopUp(
    close :()->Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    val defaultKeymaps = defaultKeymaps()
    val neoConfigViewModel= viewModel { NeoConfigureViewModel() }
    val keymaps=neoConfigViewModel.keymapApi.map { it.savedKeymaps }.getOrElse { emptyList() }
    with(sharedTransitionScope) {
        Card(
            modifier = Modifier
                .sharedBounds(
                    rememberSharedContentState(key = "container"),
                    animatedVisibilityScope = animatedVisibilityScope
                )
        ) {
            Column(
                modifier = Modifier
                    .size(1000.dp,400.dp)
                    .padding(top = 20.dp,)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Keymaps",
                    modifier = Modifier
                        .sharedElement(
                            rememberSharedContentState(key = "title"),
                            animatedVisibilityScope = animatedVisibilityScope
                        ),
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.primary
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())

                ) {

                    KeymapRow(defaultKeymaps) {
                        neoConfigViewModel.openKeymap(it)
                        close()
                    }
                    KeymapRow(keymaps) {
                        neoConfigViewModel.openKeymap(it)
                        close()
                    }
                }
            }
        }
    }
}
