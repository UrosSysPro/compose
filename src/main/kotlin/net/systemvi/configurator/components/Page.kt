package net.systemvi.configurator.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

sealed class Page
object ConfigurePage:Page()
object TesterPage:Page()
object DesignPage:Page()
object SettingsPage:Page()

class PageViewModel: ViewModel() {
    var currentPage:Page by mutableStateOf(ConfigurePage)

}