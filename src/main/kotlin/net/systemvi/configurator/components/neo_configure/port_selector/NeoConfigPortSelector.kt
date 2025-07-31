package net.systemvi.configurator.components.neo_configure.port_selector

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
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
import arrow.core.None
import arrow.core.getOrElse
import arrow.core.some
import net.systemvi.configurator.components.common.hero_pop_up.HeroPopUp
import net.systemvi.configurator.components.neo_configure.NeoConfigureViewModel
import net.systemvi.configurator.utils.services.SerialApiService


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NeoConfigPortSelector(){
    HeroPopUp (
        expanded = false,
        horizontalAlignment = Alignment.End,
        verticalAlignment = Alignment.Top,
        firstComponent = { animatedVisibilityScope, sharedTransitionScope ->
            ShowPortsButton(
                sharedTransitionScope,
                animatedVisibilityScope,
            )
        },
        secondComponent = { animatedVisibilityScope, sharedTransitionScope ->
            PortsPopUp(
                sharedTransitionScope,
                animatedVisibilityScope,
            )
        }
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun ShowPortsButton(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    with(sharedTransitionScope) {
        ElevatedButton(
            onClick = {

            },
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
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    val neoConfigViewModel = viewModel { NeoConfigureViewModel() }
    val portNames by remember { mutableStateOf(neoConfigViewModel.serialApi.map { it.getPortNames() }.getOrElse { emptyList() }) }

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
                    .combinedClickable{
//                        onCollapse()
                    }
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

                TextButton(
                    onClick = {
                        neoConfigViewModel.selectPort(None)
//                        onCollapse()
                    }
                ){
                    Text("None")
                }

                portNames.forEach { portName ->
                    TextButton(
                        onClick = {
                            neoConfigViewModel.selectPort(portName.some())
//                            onCollapse()
                        }
                    ){
                        Text(portName)
                    }
                }
            }
        }
    }
}
