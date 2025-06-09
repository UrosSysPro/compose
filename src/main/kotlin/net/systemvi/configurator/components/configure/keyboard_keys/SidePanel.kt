package net.systemvi.configurator.components.configure.keyboard_keys

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable fun SidePanel(currentPage: CurrentPage,onSelect: (CurrentPage) -> Unit) {
    val pages=listOf(CurrentPage.All,CurrentPage.Alphabet, CurrentPage.Numbers, CurrentPage.Symbols)
    Column(Modifier.padding(vertical = 8.dp, horizontal = 16.dp).width(170.dp)) {
        for(page in pages){
            TextButton(
                border = if(currentPage==page)
                    BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
                else
                    null,
                onClick = {onSelect(page)}){
                Text(page.title)
            }
        }
    }}