package net.systemvi.configurator.components.keyboard_layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SidePanel(){
    data class Link(val name:String,val onClick:()->Unit={})
    val links = listOf(
        Link("Keymap"),
        Link("Layout"),
        Link("Macros"),
        Link("Save and Load"),
    )
    Column(Modifier.padding(vertical = 8.dp, horizontal = 16.dp).width(170.dp)) {
        for(link in links){
            TextButton(onClick = link.onClick){
                Text(link.name)
            }
        }
    }
}