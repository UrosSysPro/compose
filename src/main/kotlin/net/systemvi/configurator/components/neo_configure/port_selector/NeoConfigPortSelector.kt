package net.systemvi.configurator.components.neo_configure.port_selector

import androidx.compose.animation.*
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import arrow.core.None
import arrow.core.Option
import arrow.core.getOrElse
import arrow.core.some
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import net.systemvi.configurator.components.common.hero_pop_up.HeroPopUp
import net.systemvi.configurator.components.neo_configure.NeoConfigureViewModel
import net.systemvi.configurator.model.padding
import net.systemvi.configurator.utils.services.SerialApiService


@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalComposeUiApi::class)
@Composable
fun NeoConfigPortSelector(expanded: Boolean,setExpanded: (Boolean) -> Unit){
    var job by remember { mutableStateOf<Option<Job>>(None) }
    HeroPopUp (
        expanded = expanded,
        horizontalAlignment = Alignment.End,
        verticalAlignment = Alignment.Top,
        firstComponent = { animatedVisibilityScope, sharedTransitionScope ->
            ShowPortsButton(
                {setExpanded(true)},
                job.map { it.isActive }.getOrElse {false},
                sharedTransitionScope,
                animatedVisibilityScope,
            )
        },
        secondComponent = { animatedVisibilityScope, sharedTransitionScope ->
            PortsPopUp(
                {setExpanded(false)},
                {job = it.some()},
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
    jobRunning:Boolean,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    with(sharedTransitionScope) {
        val infiniteTransition = rememberInfiniteTransition()
        val rotation by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            )
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            if(jobRunning)Icon(
                Icons.Filled.Refresh,
                "job running animation",
                modifier = Modifier
                    .padding(end = 20.dp)
                    .rotate(rotation)
            )
            ElevatedButton(
                onClick = {
                    if(!jobRunning)open()
                },
                enabled = !jobRunning,
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
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun PortsPopUp(
    close:()->Unit,
    jobStarted:(Job)->Unit,
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
                    onClick = {
                        jobStarted(scope.launch {
                            neoConfigViewModel.selectPort(scope, None)
                        })
                        close()
                    }
                ){
                    Text("None")
                }

                portNames.forEach { portName ->
                    TextButton(
                        onClick = {
                            jobStarted(scope.launch {
                                neoConfigViewModel.selectPort(scope,portName.some())
                            })
                            close()
                        }
                    ){
                        Text(portName)
                    }
                }
            }
        }
    }
}
