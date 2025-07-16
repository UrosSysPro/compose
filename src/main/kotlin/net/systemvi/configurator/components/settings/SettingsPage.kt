package net.systemvi.configurator.components.settings

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import net.systemvi.configurator.utils.services.AppStateService

@Composable fun SettingsEntry(left:@Composable () -> Unit, right:@Composable () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        left()
        right()
    }
    HorizontalDivider()
}

@OptIn(ExperimentalFoundationApi::class)
@Composable fun SettingsPage(modifier: Modifier) {
    val appStateService=viewModel { AppStateService() }
    Column(
        modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Column(
            modifier = Modifier.width(500.dp)
        ) {
            SettingsEntry(
                left={
                    Text("Primary color")
                },
                right={
                    FlowRow{
                        appStateService.colorSeeds.forEach { colorSeed ->
                            val selected = colorSeed==appStateService.primaryColor
                            val borderWidth by animateDpAsState(
                                targetValue = if (selected) 3.dp else 2.dp,
                            )
                            Box(modifier = Modifier
                                .background(
                                    color = colorSeed,
                                    shape = CircleShape,
                                )
                                .border(borderWidth, appStateService.colorScheme.secondary, CircleShape)
                                .size(30.dp)
                                .padding(start = 5.dp,top=5.dp)
                                .onClick(onClick = {appStateService.setTheme(colorSeed)})
                                .clip(CircleShape)
                            ){}
                        }
                    }
                },
            )
            SettingsEntry(
                left={
                    Text("Dark mode")
                },
                right={
                    Switch(
                        appStateService.isDark,
                        onCheckedChange = {appStateService.setTheme(isDark = it) }
                    )
                }
            )
        }
    }
}