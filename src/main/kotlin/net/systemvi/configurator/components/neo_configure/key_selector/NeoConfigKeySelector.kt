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

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun ShowKeysButton(
    open:()->Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) = with(sharedTransitionScope) {
    ElevatedButton(
        onClick = open,
        modifier = Modifier
            .sharedBounds(
                rememberSharedContentState(key = "container"),
                animatedVisibilityScope = animatedVisibilityScope
            )
    ){
        Text(
            text = "Keys",
            modifier = Modifier
                .sharedElement(
                    rememberSharedContentState(key = "title"),
                    animatedVisibilityScope = animatedVisibilityScope
                )
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun KeysPopUp(
    close:()->Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) = with(sharedTransitionScope) {
    val categories = KeyboardKeysPages.entries
    var selectedCategory by remember { mutableStateOf(categories[0]) }
    val neoConfigViewModel = viewModel { NeoConfigureViewModel() }
    Card(
        modifier = Modifier
            .sharedBounds(
                rememberSharedContentState(key = "container"),
                animatedVisibilityScope = animatedVisibilityScope
            )
    ) {
        Column(
            modifier = Modifier
                .size(600.dp,400.dp)
                .padding(top = 20.dp,),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Keys",
                modifier = Modifier
                    .sharedElement(
                        rememberSharedContentState(key = "title"),
                        animatedVisibilityScope = animatedVisibilityScope
                    ),
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary
            )
            //content
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ){
                    categories.forEach { category ->
                        IconButton(
                            onClick = {selectedCategory=category}
                        ){
                            Icon(
                                imageVector = Icons.Filled.KeyboardArrowUp,
                                contentDescription = "keys category icon",
                            )
                        }
                    }
                }
                Text(selectedCategory.title)
                FlowRow(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ){
                    when (selectedCategory) {
                        KeyboardKeysPages.LayerKeys -> {
                            for(i in 1 until 4){
                                ElevatedButton(
                                    onClick = {
                                        neoConfigViewModel.setLayerKey(i)
                                        close()
                                    }
                                ){
                                    Text("L${i+1}")
                                }
                            }
                        }
                        KeyboardKeysPages.MacroKeys -> {

                        }
                        else -> selectedCategory.keys.forEach { key ->
                        ElevatedButton(
                            onClick = {
                                neoConfigViewModel.setKey(key)
                                close()
                            }
                        ){
                            Text(key.name)
                        }
                    }
                    }
                }
            }
        }
    }
}
