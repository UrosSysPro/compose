package net.systemvi.configurator.components.neo_configure

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
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
import net.systemvi.configurator.components.configure.keyboard_layout.KeymapPreview
import net.systemvi.configurator.data.defaultKeymaps

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NeoConfigKeymapSelector(){
    var expanded by remember {
        mutableStateOf(false)
    }
    SharedTransitionLayout(
        modifier = Modifier
            .height(30.dp)
            .wrapContentHeight(unbounded = true)
    ){
        AnimatedContent(
            expanded,
            label = "basic_transition",
        ) { targetState ->
            if (!targetState) {
                ShowKeymapsButton(
                    onExpand = {
                        expanded = true
                    },
                    animatedVisibilityScope = this@AnimatedContent,
                    sharedTransitionScope = this@SharedTransitionLayout
                )
            } else {
                KeymapsPopUp(
                    onCollapse = {
                        expanded = false
                    },
                    animatedVisibilityScope = this@AnimatedContent,
                    sharedTransitionScope = this@SharedTransitionLayout
                )
            }
        }
    }
}
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun ShowKeymapsButton(
    onExpand: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    with(sharedTransitionScope) {
        ElevatedButton(
            onClick = onExpand,
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
    onCollapse: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    val keymaps = defaultKeymaps()
    with(sharedTransitionScope) {
        Card(
            modifier = Modifier
                .sharedBounds(
                    rememberSharedContentState(key = "container"),
                    animatedVisibilityScope = animatedVisibilityScope
                )
                .padding(top=450.dp)
        ) {
            Column(
                modifier = Modifier
                    .clickable { onCollapse() }
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
                        KeymapPreview(it)
                    }
                }
            }
        }
    }
}