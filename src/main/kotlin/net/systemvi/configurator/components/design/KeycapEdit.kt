package net.systemvi.configurator.components.design

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import net.systemvi.configurator.components.common.icons.HeightIcon
import net.systemvi.configurator.components.common.icons.PaddingIcon
import net.systemvi.configurator.components.common.icons.SettingsIcon
import net.systemvi.configurator.components.common.icons.WidthIcon
import net.systemvi.configurator.components.configure.KeycapPosition
import net.systemvi.configurator.model.*

@Composable
fun <F>DropDownButton(list: List<F>, onSelect: (F) -> Unit, property: F) {
    var showDropdown by remember { mutableStateOf(false) }

    Box() {
        ElevatedButton(
            onClick = { showDropdown = true },
        ) {
            Text("$property")
        }
        DropdownMenu(
            expanded = showDropdown,
            onDismissRequest = { showDropdown = false }
        ) {
            for (item in list) {
                DropdownMenuItem(
                    text = { Text("$item") },
                    onClick = { onSelect(item); showDropdown = false}
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IconWithText(text: String, icon: ImageVector, textColor: Color, modifier: Modifier = Modifier) {
    Row(modifier = Modifier.padding(horizontal = 4.dp)){
        Icon(
            imageVector = icon,
            contentDescription = "$text Icon",
            tint = textColor,
            modifier = modifier
        )
        Text(
            text = text,
            color = textColor,
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RowWithIconAndDropdown(iconContent: @Composable () -> Unit, dropdownContent: @Composable () -> Unit){
    var isHovered by remember { mutableStateOf(false) }
    val borderWidth by animateDpAsState(targetValue = if (isHovered) 3.dp else 0.dp)
    val borderColor = MaterialTheme.colorScheme.primaryContainer

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 15.dp)
            .onPointerEvent(PointerEventType.Enter) {
                isHovered = true
            }
            .onPointerEvent(PointerEventType.Exit) {
                isHovered = false
            }
            .then(
                if(borderWidth > 0.dp) Modifier.border(width = borderWidth, color = borderColor, shape = RoundedCornerShape(10.dp))
                else Modifier
            )
    ){
        iconContent()
        dropdownContent()
    }
}

@Composable
fun KeycapEdit(keymap: KeyMap, selectedKeycap: KeycapPosition, onUpdate: (keymap: KeyMap) -> Unit ){
    val textColor = MaterialTheme.colorScheme.primaryContainer
    val contentColor = MaterialTheme.colorScheme.primary
    val viewModel = viewModel { DesignPageViewModel() }
    val x = selectedKeycap.x
    val y = selectedKeycap.y

    if(viewModel.selectedKeycap != null) {
        Dialog(
            onDismissRequest = { viewModel.selectedKeycap = null }
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .size(400.dp)
                    .background(
                        color = contentColor,
                    )
                    .border(width = 1.dp, color = textColor, shape = RoundedCornerShape(10.dp))
            ) {
                IconWithText(text = "Settings", icon = SettingsIcon, textColor = textColor)
                RowWithIconAndDropdown(
                    iconContent = {
                        IconWithText("Width", icon = WidthIcon, textColor = textColor)
                    },
                    dropdownContent = {
                        DropDownButton(KeycapWidth.entries, {
                            onUpdate(keymap.setKeycapWidth(x, y, it))
                        }, keymap.keycaps[x][y].width)
                    }
                )
                RowWithIconAndDropdown(
                    iconContent = {
                        IconWithText("Height", icon = HeightIcon, textColor = textColor)
                    },
                    dropdownContent = {
                        DropDownButton(KeycapHeight.entries, {
                            onUpdate(keymap.setKeycapHeight(x, y, it))
                        }, keymap.keycaps[x][y].height)
                    }
                )
                RowWithIconAndDropdown(
                    iconContent = {
                        IconWithText("Left Padding", icon = PaddingIcon, textColor = textColor)
                    },
                    dropdownContent = {
                        DropDownButton(listOf(0f, 0.25f, 0.5f, 1f, 1.25f), {
                            onUpdate(keymap.setKeycapLeftPadding(x, y, KeycapPadding(left = it)))
                        }, keymap.keycaps[x][y].padding.left)
                    }
                )
                RowWithIconAndDropdown(
                    iconContent = {
                        IconWithText("Bottom padding", PaddingIcon, textColor, Modifier.rotate(-90f))
                    },
                    dropdownContent = {
                        DropDownButton(listOf(0f, 0.25f, 0.5f, 1f, 1.25f), {
                            onUpdate(keymap.setKeycapBottomPadding(x, y, KeycapPadding(bottom = it)))
                        }, keymap.keycaps[x][y].padding.bottom)
                    }
                )
            }
        }
    }
}