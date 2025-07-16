package net.systemvi.configurator.components.common.navbar

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import net.systemvi.configurator.model.Page

data class NavbarLink(val title:String, val page: Page, val icon: ImageVector,val hoverCard:@Composable () -> Unit = {})
