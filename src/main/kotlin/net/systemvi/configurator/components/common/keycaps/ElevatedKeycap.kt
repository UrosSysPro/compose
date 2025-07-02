package net.systemvi.configurator.components.common.keycaps

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import arrow.core.getOrElse
import net.systemvi.configurator.components.common.keyboard_grid.KeycapParam
import net.systemvi.configurator.components.tester.TesterPageViewModel
import net.systemvi.configurator.data.allKeys
import net.systemvi.configurator.model.offset

//TODO NAPRAVITI OVO

val ElevatedKeycap = @Composable { param: KeycapParam ->
    val viewModel = viewModel { TesterPageViewModel() }
    val key = param.keycap.layers[0].getOrElse { allKeys.last() }
    val currentlyClicked = viewModel.currentlyDownKeys.contains(key)
    val wasClicked = viewModel.wasDownKeys.contains(key)

//    val radius = if(currentlyClicked) 20.dp else 6.dp
//    val radius by animateDpAsState(targetValue = if (currentlyClicked) 4.dp else 12.dp)
    val radius=4.dp
    val elevation by animateDpAsState(targetValue = if (currentlyClicked) 0.3.dp else 2.dp)
    val ee by animateFloatAsState(targetValue = if (currentlyClicked) 1f else 6f)
    val offset by animateDpAsState(targetValue = if (currentlyClicked) 5.dp else 0.dp)
    val e= CardDefaults.cardElevation(elevation)
//    Box(
//        modifier = Modifier.padding(5.dp).fillMaxSize()
//            .offset(0.dp,offset)
//            .shadow(
//                elevation = elevation,
//                shape = RoundedCornerShape(radius),
//                clip = false,
//                ambientColor = Color(0xffff0000)
//            )
//            .background(MaterialTheme.colorScheme.primary,RoundedCornerShape(radius))
//    ){}
    Box (
//        elevation= e,
        modifier = Modifier.padding(4.dp).fillMaxSize()
//            .graphicsLayer{
//            shape= RoundedCornerShape(radius)
//                shadowElevation=ee
//                clip=false
//            }
            .shadow(
                shape = RoundedCornerShape(radius),
                elevation = elevation,
                clip = false,
//                ambientColor = MaterialTheme.colorScheme.primary,
//                spotColor = MaterialTheme.colorScheme.primary,
                ambientColor = Color(0xff0000ff),
                spotColor = Color(0xff0000ff)
            )
            .background(
                shape = RoundedCornerShape(radius),
                color = MaterialTheme.colorScheme.surfaceDim,
            )
    ){  }
//    val textColor = when {
//        currentlyClicked -> MaterialTheme.colorScheme.tertiaryContainer
//        wasClicked -> MaterialTheme.colorScheme.primaryContainer
//        else -> MaterialTheme.colorScheme.primary
//    }
//
//    val containerColor = when {
//        currentlyClicked -> MaterialTheme.colorScheme.tertiary
//        wasClicked -> MaterialTheme.colorScheme.primary
//        else -> MaterialTheme.colorScheme.primaryContainer
//    }
//
//    val elevation by remember {
//        animateDpAsState(
//            targetValue =
//                when {
//                    currentlyClicked -> 1.dp
//                    else -> 32.dp
//                }
//        )
//    }
//
//
//
//    LaunchedEffect(currentlyClicked) {
//        val note = param.position.x + param.position.y * 12 + 40
//        val velocity = 93
//        if (currentlyClicked) {
//            println(note)
//            viewModel.channels?.get(0)?.noteOn(note, velocity)
//        } else {
//            viewModel.channels?.get(0)?.noteOff(note, velocity)
//        }
//    }
//
////    Card(
////        elevation = CardDefaults.cardElevation(
////            defaultElevation = 6.dp
////        ),
////        modifier = Modifier
////            .fillMaxSize()
////            .padding(3.dp)
////            .clip(RoundedCornerShape(10.dp))
////            .background(
////                color =
////                    when {
////                        currentlyClicked -> MaterialTheme.colorScheme.tertiary
////                        wasClicked -> MaterialTheme.colorScheme.primary
////                        else -> MaterialTheme.colorScheme.primaryContainer
////                    }
////            ),
////    ) {
////        Text(
////            key.name,
////            style = MaterialTheme.typography.bodySmall,
////            color =
////                when {
////                    currentlyClicked -> MaterialTheme.colorScheme.tertiaryContainer
////                    wasClicked -> MaterialTheme.colorScheme.primaryContainer
////                    else -> MaterialTheme.colorScheme.primary
////                },
////            textAlign = TextAlign.Center
////        )
////    }
//
//    Card(
//        elevation = CardDefaults.cardElevation(
//            defaultElevation = elevation
//        ),
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(5.dp),
//        colors = CardDefaults.cardColors(
//            containerColor = MaterialTheme.colorScheme.primary
//        ),
//    ){
//        Text(
//            key.name,
//            style = MaterialTheme.typography.bodySmall,
//            color = MaterialTheme.colorScheme.primaryContainer,
//            textAlign = TextAlign.Center,
//            modifier = Modifier
//                .fillMaxSize()
//                .wrapContentSize(Alignment.Center),
//        )
//    }
}

