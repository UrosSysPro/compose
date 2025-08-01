package net.systemvi.configurator.components.neo_configure.key_selector

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import net.systemvi.configurator.model.SnapTapPair

@Composable
fun KeyCategorySnapTap(
    snapTapPairs:List<SnapTapPair>,
    onAddSnapTap:() -> Unit,
    onRemoveSnapTap:(SnapTapPair) -> Unit,
){
    ElevatedButton(
        onClick = onAddSnapTap
    ){
        Text(text = "+")
    }
    snapTapPairs.forEach { pair ->
        ElevatedButton(
            onClick = { },
        ){
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
            ){
                var showMenu by remember { mutableStateOf(false) }
                Text("snap tap pair")
                Icon(
                    Icons.Filled.MoreVert,
                    contentDescription = "three dots",
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .combinedClickable{showMenu = true}
                )
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false },
                ){
                    DropdownMenuItem(
                        text = { Text("Delete") },
                        onClick = { onRemoveSnapTap(pair); showMenu = false }
                    )
                }
            }
        }
    }
}