package net.systemvi.configurator.components.configure.keyboard_layout

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SidePanel(currentPage: CurrentPage,onSelect: (CurrentPage)->Unit){
    val pages = listOf(
        CurrentPage.Keymap,
        CurrentPage.Layouts,
        CurrentPage.SaveAndLoad,
    )
    Column(Modifier.padding(vertical = 8.dp, horizontal = 16.dp).width(170.dp)) {
        for(page in pages){
            val selected=currentPage==page
            TextButton(
                border = if(selected)
                    BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
                else
                    null,
                colors = ButtonDefaults.textButtonColors(
                    containerColor = if(selected)
                        MaterialTheme.colorScheme.primaryContainer
                    else
                        Color.Transparent,
                ),
                onClick = {onSelect(page)}){
                Text(page.title)
            }
        }
    }
}