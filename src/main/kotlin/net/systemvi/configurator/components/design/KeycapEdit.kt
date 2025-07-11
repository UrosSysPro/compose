package net.systemvi.configurator.components.design

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import net.systemvi.configurator.components.common.icons.HeightIcon
import net.systemvi.configurator.components.common.icons.PaddingIcon
import net.systemvi.configurator.components.common.icons.WidthIcon
import net.systemvi.configurator.model.*

@Composable
fun <F>DropDownButton(list: List<F>, onSelect: (F) -> Unit) {
    var showDropdown by remember { mutableStateOf(false) }
    Box() {
        ElevatedButton(
            onClick = { showDropdown = true },
        ) {
            Text("${list[0]}")
        }
        DropdownMenu(
            expanded = showDropdown,
            onDismissRequest = { showDropdown = false }
        ) {
            for (item in list) {
                DropdownMenuItem(
                    text = { Text("$item") },
                    onClick = {onSelect(item); showDropdown = false }
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KeycapEdit( show: Boolean, keymap: KeyMap, x: Int, y: Int, onUpdate: (keymap: KeyMap) -> Unit ){
    val textColor = MaterialTheme.colorScheme.primaryContainer

    if(show) {
        Box(
            modifier = Modifier
                .height(350.dp)
                .width(300.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                )
        ) {
            Column() {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ){
                    Icon(
                        imageVector = WidthIcon,
                        contentDescription = "Width Icon",
                        tint = textColor,
                    )
                    Text(
                        text = "Width",
                        color = textColor,
                    )
                    DropDownButton(KeycapWidth.entries, {
                        onUpdate(keymap.setKeycapWidth(x, y, it))
                    })
                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ){
                    Icon(
                        imageVector = HeightIcon,
                        contentDescription = "Height Icon",
                        tint = textColor,
                    )
                    Text(
                        text = "Height",
                        color = textColor,
                    )
                    DropDownButton(KeycapHeight.entries, {
                        onUpdate(keymap.setKeycapHeight(x, y, it))
                    })
                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ){
                    Icon(
                        imageVector = PaddingIcon,
                        contentDescription = "Left Padding Icon",
                        tint = textColor
                    )
                    Text(
                        text = "Left Padding",
                        color = textColor
                    )
                    DropDownButton(listOf(0f, 0.25f, 0.5f, 1f, 1.25f), {
                        onUpdate(keymap.setKeycapLeftPadding(x, y, KeycapPadding(left = it)))
                    })
                }

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ){
                    Icon(
                        imageVector = PaddingIcon,
                        contentDescription = "Bottom Padding Icon",
                        tint = textColor,
                        modifier = Modifier.rotate(-90f)
                    )
                    Text(
                        text = "Bottom Padding",
                        color = textColor
                    )
                    DropDownButton(listOf(0f, 0.25f, 0.5f, 1f, 1.25f), {
                        onUpdate(keymap.setKeycapBottomPadding(x, y, KeycapPadding(bottom = it)))
                    })
                }
            }
        }
    }
}