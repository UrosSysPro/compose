package net.systemvi.configurator.components.neo_configure.port_selector

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import net.systemvi.configurator.components.common.hero_pop_up.HeroPopUp
import net.systemvi.configurator.utils.services.SerialApiService


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NeoConfigPortSelector(){
    HeroPopUp (
        horizontalAlignment = Alignment.End,
        verticalAlignment = Alignment.Top,
        firstComponent = {onCollapse, animatedVisibilityScope, sharedTransitionScope ->
            ShowPortsButton(
                onCollapse,
                sharedTransitionScope,
                animatedVisibilityScope,
            )
        },
        secondComponent = {onCollapse, animatedVisibilityScope, sharedTransitionScope ->
            PortsPopUp(
                onCollapse,
                sharedTransitionScope,
                animatedVisibilityScope,
            )
        }
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun ShowPortsButton(
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
                text = "Ports",
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
private fun PortsPopUp(
    onCollapse: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    val serialApi = viewModel { SerialApiService() }.serialApi
    val portNames by remember { mutableStateOf(serialApi.getPortNames()) }

    with(sharedTransitionScope) {
        Card(
            modifier = Modifier
                .sharedBounds(
                    rememberSharedContentState(key = "container"),
                    animatedVisibilityScope = animatedVisibilityScope
                )
        ) {
            Column(
                modifier = Modifier
                    .clickable { onCollapse() }
                    .size(300.dp,400.dp)
                    .padding(top = 20.dp,),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Ports",
                    modifier = Modifier
                        .sharedElement(
                            rememberSharedContentState(key = "title"),
                            animatedVisibilityScope = animatedVisibilityScope
                        ),
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.primary
                )
                portNames.forEach { portName ->
                    TextButton(
                        onClick = {}
                    ){
                        Text(portName)
                    }
                }
            }
        }
    }
}
