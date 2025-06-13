package net.systemvi.configurator.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.materialkolor.dynamicColorScheme

sealed class Page
object ConfigurePage:Page()
object TesterPage:Page()
object DesignPage:Page()
object SettingsPage:Page()

class ApplicationViewModel: ViewModel() {
    var currentPage:Page by mutableStateOf(ConfigurePage)

    val colorSeeds=listOf(
        Color(0xEF3E36),
        Color(0x17BEBB),
        Color(0x2E282A),
        Color(0xEDB88B),
        Color(0xFAD8D6),
        Color(0x533A71),
        Color(0x6184D8),
        Color(0x50C5B7),
        Color(0x9CEC5B),
        Color(0xF0F465),
        Color(0xF49D37),
        Color(0x140F2D),
    )

    private var isDark by mutableStateOf(false)
    private var currentColor by mutableStateOf(colorSeeds[5])
    var colorScheme by mutableStateOf(dynamicColorScheme(currentColor, isDark))

    fun setDarkTheme(darkTheme:Boolean){
        this.isDark = darkTheme
        colorScheme=dynamicColorScheme(currentColor, darkTheme)
    }
    fun getDarkTheme() = isDark
    fun setSelectedColor(color:Int){
        this.currentColor = colorSeeds[color]
        colorScheme=dynamicColorScheme(currentColor, isDark)
    }
    fun setSelectedColor(color:Color){
        this.currentColor = color
        colorScheme=dynamicColorScheme(currentColor, isDark)
    }
}