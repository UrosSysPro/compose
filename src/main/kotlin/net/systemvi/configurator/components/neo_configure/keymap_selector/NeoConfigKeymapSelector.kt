package net.systemvi.configurator.components.neo_configure.keymap_selector

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import net.systemvi.configurator.components.common.hero_pop_up.HeroPopUp

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