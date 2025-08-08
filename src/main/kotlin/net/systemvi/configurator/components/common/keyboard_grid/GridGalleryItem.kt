package net.systemvi.configurator.components.common.keyboard_grid

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import net.systemvi.configurator.components.common.keycaps.FlatKeycap
import net.systemvi.configurator.components.tester.keycaps.FlatKeycap
import net.systemvi.configurator.data.defaultKeymaps
import net.systemvi.configurator.utils.annotations.ComposablesGalleryItem

@ComposablesGalleryItem("Keymap Grid")
@Composable
fun GridGalleryItem(){
    Grid(defaultKeymaps()[0],{ FlatKeycap(false,false,"") },20)
}