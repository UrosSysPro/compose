package net.systemvi.configurator.components.configure.keyboard_layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.systemvi.configurator.components.common.keyboard_grid.KeycapComponent

val KeyboardPreviewKeycapComponent: KeycapComponent={params->
    Box(
        modifier = Modifier.fillMaxSize().padding(2.dp).background(MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(1.dp))
    ){}
}