package net.systemvi.configurator.components.common.keycaps

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.materialkolor.ktx.blend
import net.systemvi.configurator.data.LayerKeyColors
import net.systemvi.configurator.data.SnapTapKeyColors

@OptIn(ExperimentalFoundationApi::class)
@Composable fun ConfiguratorKeycap(
    //user input
    isDown:Boolean,
    isSelected:Boolean,
    text:String,
    onClick:()->Unit={},
    //for layer keys
    isLayerKey:Boolean=false,
    layer:Int=-1,
    //for snap tap keys
    isSnapTapKey:Boolean=false,
    isFirstSnapTapKey:Boolean=false,
    snapTapPairIndex:Int=-1,
    isCurrentlySelectingSnapTapKeys:Boolean=false,
    ){

    val backgroundColor by animateColorAsState(when{
        isCurrentlySelectingSnapTapKeys -> MaterialTheme.colorScheme.surfaceDim
        isLayerKey-> LayerKeyColors[layer-1].blend(Color.Black,when{
            isDown->0.1f
            isSelected->0.2f
            else-> 0f
        })
        isDown-> MaterialTheme.colorScheme.tertiaryContainer
        isSelected->MaterialTheme.colorScheme.primary
        else->MaterialTheme.colorScheme.secondaryContainer
    })

    val borderColor by animateColorAsState(when{
        isSnapTapKey -> SnapTapKeyColors[snapTapPairIndex%SnapTapKeyColors.size]
        else -> MaterialTheme.colorScheme.secondary
    })

    val borderWidth by animateDpAsState(when{
        isSnapTapKey->3.dp
        else->2.dp
    })

    val snapTapText:@Composable ()->Unit=
        when{
            isSnapTapKey-> {{
                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier.fillMaxSize().padding()
                ) {
                    Text(
                        if(isFirstSnapTapKey)"1st" else "2nd",
                        modifier = Modifier
                            .background(borderColor, shape = RoundedCornerShape(bottomStart = 4.dp))
                            .padding(vertical = 0.dp,horizontal = 8.dp),
                        color = Color.White,
                        fontSize = 8.sp,
                    )
                }
            }}
            else ->  {{}}
        }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(2.dp)
            .clip(RoundedCornerShape(10.dp))
            .onClick(onClick = onClick)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(10.dp)
            )
            .border(
                BorderStroke(borderWidth,borderColor),
                shape = RoundedCornerShape(10.dp)
            )
    ){
        Text(
            text,
            fontSize = MaterialTheme.typography.bodySmall.fontSize,
            modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center),
            textAlign = TextAlign.Center
        )
        snapTapText()
    }
}