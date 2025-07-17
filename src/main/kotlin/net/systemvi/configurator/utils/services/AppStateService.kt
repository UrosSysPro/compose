package net.systemvi.configurator.utils.services

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.materialkolor.dynamicColorScheme
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import net.systemvi.configurator.model.ConfigurePage
import net.systemvi.configurator.model.Page
import java.io.File
import java.io.FileWriter
import java.util.Scanner

@Serializable
data class AppConfig(val primaryColor: ULong, val isDark: Boolean,val currentPage: Page)

class AppStateService: ViewModel() {

    var currentPage: Page by mutableStateOf(ConfigurePage)

    val colorSeeds=listOf(
        Color(0xffEF3E36),
        Color(0xff17BEBB),
        Color(0xff2E282A),
        Color(0xffEDB88B),
        Color(0xffFAD8D6),
        Color(0xff533A71),
        Color(0xff6184D8),
        Color(0xff50C5B7),
        Color(0xff9CEC5B),
        Color(0xffF0F465),
        Color(0xffF49D37),
        Color(0xff140F2D),
    )

    var isDark by mutableStateOf(false)
        private set
    var primaryColor by mutableStateOf(colorSeeds[5])
        private set
    var colorScheme by mutableStateOf(dynamicColorScheme(primaryColor, isDark))
        private set

    var useClientDecoration by mutableStateOf(false)

    fun setTheme(primaryColor:Color? = null,isDark:Boolean? = null){
        if(isDark!=null)this.isDark=isDark
        if(primaryColor!=null)this.primaryColor=primaryColor
        colorScheme=dynamicColorScheme(this.primaryColor,this.isDark)
    }

    fun saveToDisk(){
        try{
            val config = AppConfig(primaryColor.value,isDark,currentPage)
            val json = Json.encodeToString(config)
            val writer= FileWriter("config.json")
            writer.write(json)
            writer.close()
            println("[INFO] successfuly saved app config to disk")
        }catch (e: Exception){
            println("[ERROR] error while saving app config to disk")
            e.printStackTrace()
        }
    }

    fun loadFromDisk(){
        try {
            val file= File("config.json")
            val scanner= Scanner(file)
            val json= StringBuilder()
            while(scanner.hasNextLine())json.append(scanner.nextLine())
            val config=Json.decodeFromString<AppConfig>(json.toString())
            currentPage=config.currentPage
            setTheme(Color(config.primaryColor),config.isDark)
            println("[INFO] successfully loaded config from disk")
        }catch (e: Exception){
            println("[ERROR] error while loading app config from disk")
            e.printStackTrace()
        }
    }

    fun onStart(){
        loadFromDisk()
        println("[INFO] App state service start")
    }

    fun onStop(){
        saveToDisk()
        println("[INFO] App state service stop")
    }
}