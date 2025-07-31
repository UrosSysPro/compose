package net.systemvi.configurator.components.common.hero_pop_up

import androidx.compose.animation.*
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HeroPopUp(
    expanded:Boolean,
    width: Dp=30.dp,
    height: Dp=30.dp,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    animationLabel: String = "basic_transition",
    firstComponent:@Composable (
        animatedVisibilityScope: AnimatedVisibilityScope,
        sharedTransitionScope: SharedTransitionScope
    ) -> Unit = {_,_->},
    secondComponent: @Composable (
        animatedVisibilityScope: AnimatedVisibilityScope,
        sharedTransitionScope: SharedTransitionScope
    ) -> Unit = {_,_->},
){
    SharedTransitionLayout(
        modifier = Modifier
            .width(width)
            .height(height)
            .wrapContentWidth(unbounded = true,align = horizontalAlignment)
            .wrapContentHeight(unbounded = true, align = verticalAlignment),
    ){
        AnimatedContent(
            expanded,
            label = animationLabel,
        ) { targetState ->
            if (!targetState) {
                firstComponent(
                    this@AnimatedContent,
                    this@SharedTransitionLayout,
                )
            } else {
                secondComponent(
                    this@AnimatedContent,
                    this@SharedTransitionLayout,
                )
            }
        }
    }
}