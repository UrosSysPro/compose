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
fun NeoConfigKeymapSelector(expanded: Boolean,setExpanded: (Boolean) -> Unit,){
    HeroPopUp (
        expanded = expanded,
        verticalAlignment = Alignment.Top,
        firstComponent = { animatedVisibilityScope, sharedTransitionScope ->
            ShowKeymapsButton(
                {setExpanded(true)},
                sharedTransitionScope,
                animatedVisibilityScope,
            )
        },
        secondComponent = { animatedVisibilityScope, sharedTransitionScope ->
            KeymapsPopUp(
                {setExpanded(false)},
                sharedTransitionScope,
                animatedVisibilityScope,
            )
        }
    )
}