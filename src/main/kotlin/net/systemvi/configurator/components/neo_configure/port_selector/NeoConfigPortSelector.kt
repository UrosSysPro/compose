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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import arrow.core.None
import arrow.core.getOrElse
import arrow.core.some
import kotlinx.coroutines.launch
import net.systemvi.configurator.components.common.hero_pop_up.HeroPopUp
import net.systemvi.configurator.components.neo_configure.NeoConfigureViewModel
import net.systemvi.configurator.utils.services.SerialApiService


@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalComposeUiApi::class)
@Composable
fun NeoConfigPortSelector(){
    var expanded by remember { mutableStateOf(false) }
    HeroPopUp (
        expanded = expanded,
        horizontalAlignment = Alignment.End,
        verticalAlignment = Alignment.Top,
        firstComponent = { animatedVisibilityScope, sharedTransitionScope ->
            ShowPortsButton(
                {expanded=true},
                sharedTransitionScope,
                animatedVisibilityScope,
            )
        },
        secondComponent = { animatedVisibilityScope, sharedTransitionScope ->
            PortsPopUp(
                {expanded=false},
                sharedTransitionScope,
                animatedVisibilityScope,
            )
        }
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun ShowPortsButton(
    open:()->Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    with(sharedTransitionScope) {
        ElevatedButton(
            onClick = {
                open()
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
    close:()->Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    val neoConfigViewModel = viewModel { NeoConfigureViewModel() }
    val portNames by remember { mutableStateOf(neoConfigViewModel.serialApi.map { it.getPortNames() }.getOrElse { emptyList() }) }
    val scope= rememberCoroutineScope()

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
                    onClick = { scope.launch {
                        neoConfigViewModel.selectPort(None)
                        close()
                    }}
                ){
                    Text("None")
                }

                portNames.forEach { portName ->
                    TextButton(
                        onClick = {scope.launch {
                            neoConfigViewModel.selectPort(portName.some())
                            close()
                        }}
                    ){
                        Text(portName)
                    }
                }
            }
        }
    }
}
