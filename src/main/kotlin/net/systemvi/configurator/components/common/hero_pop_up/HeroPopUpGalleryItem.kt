package net.systemvi.configurator.components.common.hero_pop_up

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.systemvi.configurator.utils.annotations.ComposablesGalleryItem

@OptIn(ExperimentalSharedTransitionApi::class)
@ComposablesGalleryItem("Hero Pop Up")
@Composable
fun HeroPopUpGalleryItem(){
    var expanded by remember { mutableStateOf(false) }
    HeroPopUp(
        expanded = expanded,
        firstComponent = { visibilityScope,transitionScope-> with(transitionScope){
            ElevatedButton(
                onClick = { expanded = !expanded },
                modifier = Modifier
                    .sharedBounds(
                        rememberSharedContentState("background"),
                        animatedVisibilityScope = visibilityScope
                    )
            ){
                Text(
                    "Small",
                    modifier = Modifier.sharedElement(
                        rememberSharedContentState("text"),
                        animatedVisibilityScope = visibilityScope
                    )
                )
            }
        }},
        secondComponent = { visibilityScope,transitionScope-> with(transitionScope){
            ElevatedButton(
                onClick = { expanded = !expanded },
                contentPadding = PaddingValues(vertical = 32.dp, horizontal = 64.dp),
                modifier = Modifier
                    .sharedBounds(
                        rememberSharedContentState("background"),
                        animatedVisibilityScope = visibilityScope
                    )
            ){
                Text(
                    "Big",
                    modifier = Modifier.sharedElement(
                        rememberSharedContentState("text"),
                        animatedVisibilityScope = visibilityScope
                    )
                )
            }
        }}
    )
}
