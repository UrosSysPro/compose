package net.systemvi.configurator.components.neo_configure.key_selector

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ShowKeysButton(
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
