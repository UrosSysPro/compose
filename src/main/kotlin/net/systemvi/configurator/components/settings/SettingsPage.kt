package net.systemvi.configurator.components.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import net.systemvi.configurator.components.ApplicationViewModel

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

@Composable fun SettingsPage(modifer: Modifier,appViewModel: ApplicationViewModel = viewModel { ApplicationViewModel() }) {
    Column(
        modifer.fillMaxSize(),
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
                    Row{
                        appViewModel.colorSeeds.forEach { colorSeed ->
                            Box(modifier = Modifier
                                .clip(CircleShape)
                                .border(0.dp, colorScheme.primary, CircleShape)
                                .background(color = colorSeed, shape = CircleShape)
                                .width(20.dp)
                                .height(20.dp)
                                .combinedClickable(onClick = {appViewModel.setSelectedColor(colorSeed)})
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
                    Switch(appViewModel.getDarkTheme(),onCheckedChange = {appViewModel.setDarkTheme(it) })
                }
            )
        }
    }
}