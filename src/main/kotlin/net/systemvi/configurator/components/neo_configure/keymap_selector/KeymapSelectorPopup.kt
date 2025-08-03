package net.systemvi.configurator.components.neo_configure.keymap_selector

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import net.systemvi.configurator.components.neo_configure.NeoConfigureViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun KeymapsPopUp(
    close :()->Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    val neoConfigViewModel= viewModel { NeoConfigureViewModel() }

    val defaultKeymaps = neoConfigViewModel.defaultKeymaps()
    val savedKeymaps = neoConfigViewModel.savedKeymaps()
    val onboardKeymaps = neoConfigViewModel.onboardKeymaps

    val keymaps = listOf(
        Pair("Default",defaultKeymaps),
        Pair("On Board",onboardKeymaps),
        Pair("Saved Keymaps",savedKeymaps),
    )

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
                    .padding(top = 20.dp)
                    .padding(horizontal = 20.dp)
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
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ) {
                    keymaps.filter { it.second.isNotEmpty() }.forEach { (title,keymaps)->
                        KeymapRow(title,keymaps){
                            neoConfigViewModel.openKeymap(it)
                            close()
                        }
                    }
                }
            }
        }
    }
}
