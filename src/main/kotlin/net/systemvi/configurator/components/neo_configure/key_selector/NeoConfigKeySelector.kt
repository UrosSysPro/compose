package net.systemvi.configurator.components.neo_configure.key_selector

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import net.systemvi.configurator.components.common.hero_pop_up.HeroPopUp
import net.systemvi.configurator.components.configure.KeyboardKeysPages
import net.systemvi.configurator.components.neo_configure.NeoConfigureViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NeoConfigKeySelector(expanded:Boolean,setExpanded:(Boolean)->Unit){
    HeroPopUp (
        expanded = expanded,
        horizontalAlignment = Alignment.Start,
        verticalAlignment = Alignment.Top,
        firstComponent = {animationScope,transitionScope->
            ShowKeysButton(
                {setExpanded(true)},
                transitionScope,
                animationScope
            )
        },
        secondComponent = {animationScope,transitionScope->
            KeysPopUp(
                {setExpanded(false)},
                transitionScope,
                animationScope
            )
        },
    )
}