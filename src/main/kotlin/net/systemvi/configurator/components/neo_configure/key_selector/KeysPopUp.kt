package net.systemvi.configurator.components.neo_configure.key_selector

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import arrow.core.getOrElse
import net.systemvi.configurator.components.configure.KeyboardKeysPages
import net.systemvi.configurator.components.neo_configure.NeoConfigureViewModel
import net.systemvi.configurator.model.Macro
import net.systemvi.configurator.model.SnapTapPair


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun KeysPopUp(
    close:()->Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) = with(sharedTransitionScope) {
    val categories = KeyboardKeysPages.entries
    var selectedCategory by remember { mutableStateOf(categories[0]) }
    val neoConfigViewModel = viewModel { NeoConfigureViewModel() }
    var macros by remember { mutableStateOf(emptyList<Macro>()) }
    val snapTapPairs=neoConfigViewModel.keymap.map { it.snapTapPairs }.getOrElse { emptyList() }

    Card(
        modifier = Modifier
            .sharedBounds(
                rememberSharedContentState(key = "container"),
                animatedVisibilityScope = animatedVisibilityScope
            )
    ) {
        Column(
            modifier = Modifier
                .size(600.dp,400.dp)
                .padding(top = 20.dp,),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = selectedCategory.title,
                modifier = Modifier
                    .sharedElement(
                        rememberSharedContentState(key = "title"),
                        animatedVisibilityScope = animatedVisibilityScope
                    ),
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary
            )
            //content
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ){
                    categories.forEach { category ->
                        IconButton(
                            onClick = {selectedCategory=category}
                        ){
                            Icon(
                                imageVector = Icons.Filled.KeyboardArrowUp,
                                contentDescription = "keys category icon",
                            )
                        }
                    }
                }
                KeyCategoryGrid(
                    page = selectedCategory,
                    macros = macros,
                    onAddMacro = {macros += it},
                    onRemoveMacro = {macros = macros.filter { macro-> macro != it }},
                    onNormalKeySelected = { key-> neoConfigViewModel.setKey(key);close()},
                    onMacroKeySelected = {macro->neoConfigViewModel.setMacro(macro);close()},
                    onLayerKeySelected = {layer->neoConfigViewModel.setLayerKey(layer);close()},
                    snapTapPairs = snapTapPairs,
                    onAddSnapTap = {println("Add snap tap pair")},
                    onRemoveSnapTap = {println("Remove snap tap pair")},
                )
            }
        }
    }
}
