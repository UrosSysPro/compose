package net.systemvi.configurator.components.neo_configure.keymap_selector

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import net.systemvi.configurator.components.common.hero_pop_up.HeroPopUp
import net.systemvi.configurator.components.configure.keyboard_layout.KeymapPreview
import net.systemvi.configurator.components.neo_configure.NeoConfigureViewModel
import net.systemvi.configurator.data.defaultKeymaps

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NeoConfigKeymapSelector(){
    var expanded by remember { mutableStateOf(false) }
    HeroPopUp (
        expanded = expanded,
        verticalAlignment = Alignment.Top,
        firstComponent = { animatedVisibilityScope, sharedTransitionScope ->
            ShowKeymapsButton(
                {expanded=true},
                sharedTransitionScope,
                animatedVisibilityScope,
            )
        },
        secondComponent = { animatedVisibilityScope, sharedTransitionScope ->
            KeymapsPopUp(
                {expanded=false},
                sharedTransitionScope,
                animatedVisibilityScope,
            )
        }
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun ShowKeymapsButton(
    open:()->Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    with(sharedTransitionScope) {
        ElevatedButton(
            onClick = open,
            modifier = Modifier
                .sharedBounds(
                    rememberSharedContentState(key = "container"),
                    animatedVisibilityScope = animatedVisibilityScope
                )
        ){
            Text(
                text = "Keymaps",
                modifier = Modifier
                    .sharedElement(
                        rememberSharedContentState(key = "title"),
                        animatedVisibilityScope = animatedVisibilityScope
                    )
            )
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun KeymapsPopUp(
    close :()->Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    val keymaps = defaultKeymaps()
    val neoConfigViewModel= viewModel { NeoConfigureViewModel() }
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
                    .padding(top = 20.dp,),
                verticalArrangement = Arrangement.Center,
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
                FlowRow(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                    ,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ){
                    keymaps.forEach {
                        KeymapPreview(it,{
                            neoConfigViewModel.openKeymap(it)
                            close()
                        })
                    }
                }
            }
        }
    }
}