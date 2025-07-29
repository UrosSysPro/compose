package net.systemvi.configurator.components.common.hero_pop_up

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HeroPopUp(
    width: Dp=30.dp,
    height: Dp=30.dp,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    animationLabel: String = "basic_transition",
    firstComponent:@Composable (
        onExpand:()->Unit,
        animatedVisibilityScope: AnimatedVisibilityScope,
        sharedTransitionScope: SharedTransitionScope
    ) -> Unit = {_,_,_->},
    secondComponent: @Composable (
        onCollapse:()->Unit,
        animatedVisibilityScope: AnimatedVisibilityScope,
        sharedTransitionScope: SharedTransitionScope
    ) -> Unit = {_,_,_->},
){
    var expanded by remember {
        mutableStateOf(false)
    }
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
                    { expanded = true },
                    this@AnimatedContent,
                    this@SharedTransitionLayout,
                )
            } else {
                secondComponent(
                    { expanded = false },
                    this@AnimatedContent,
                    this@SharedTransitionLayout,
                )
            }
        }
    }
}