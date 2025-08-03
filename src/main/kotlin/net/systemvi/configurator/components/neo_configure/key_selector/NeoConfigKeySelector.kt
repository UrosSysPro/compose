package net.systemvi.configurator.components.neo_configure.key_selector

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import net.systemvi.configurator.components.common.hero_pop_up.HeroPopUp

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NeoConfigKeySelector(expanded:Boolean,setExpanded:(Boolean)->Unit){
    HeroPopUp (
        expanded = expanded,
        horizontalAlignment = Alignment.Start,
        verticalAlignment = Alignment.Top,
        firstComponent = {animationScope,transitionScope->
            ShowKeysButton(
                { setExpanded(true) },
                transitionScope,
                animationScope
            )
        },
        secondComponent = {animationScope,transitionScope->
            KeysPopUp(
                { setExpanded(false) },
                transitionScope,
                animationScope
            )
        },
    )
}