package net.systemvi.configurator.page

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.systemvi.configurator.utils.annotations.galleryComponents

@Composable
fun ComposablesGalleryPage (){

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(32.dp),
            modifier = Modifier
                .padding(top = 32.dp)
        ) {
            Text(
                "Composables Gallery",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
            )
            galleryComponents.forEach { (name,component)->
                FlowRow (
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .width(650.dp)
                        .horizontalScroll(rememberScrollState())
                ){
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.tertiaryContainer,RoundedCornerShape(8.dp))
                            .padding(16.dp)
                            .size(300.dp)
                    ) {
                        Text(
                            name,
                            color = MaterialTheme.colorScheme.onTertiaryContainer,
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.sp,
                            modifier = Modifier
                        )
                        component()
                    }
                }
            }
        }
    }
}