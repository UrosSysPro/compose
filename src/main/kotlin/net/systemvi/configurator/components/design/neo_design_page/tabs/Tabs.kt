package net.systemvi.configurator.components.design.neo_design_page.tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import net.systemvi.configurator.components.design.DesignPageViewModel

enum class DesignPageTabs(val title: String){
    KeycapSettings("Keycap Settings"),
    KeymapSettings("Keymap Settings"),
    KeyboardJson("Keyboard JSON Format"),
    Summary("Summary")
}

@Composable
fun Tabs() {
    val viewModel = viewModel { DesignPageViewModel() }
    val currentTab = viewModel.currentTab
    val allTabs = DesignPageTabs.entries

    TabRow(
        selectedTabIndex = currentTab.ordinal
    ) {
        allTabs.forEach { tab ->
            Tab(
                selected = tab == currentTab,
                onClick = { viewModel.currentTab = tab },
                text = { Text(tab.title) }
            )
        }
    }
}
