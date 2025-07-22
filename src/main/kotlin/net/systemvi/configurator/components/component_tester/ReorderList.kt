package net.systemvi.configurator.components.component_tester

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.mohamedrejeb.compose.dnd.reorder.ReorderContainer
import com.mohamedrejeb.compose.dnd.reorder.ReorderableItem
import com.mohamedrejeb.compose.dnd.reorder.rememberReorderState

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ReorderListScreen() {
    ReorderScreenContent(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(20.dp)
    )
}


@Composable
private fun ReorderScreenContent(
    modifier: Modifier = Modifier,
) {
    val reorderState = rememberReorderState<String>()
    var items by remember {
        mutableStateOf(
            listOf(
                "item1",
                "item2",
                "item3",
                "item4",
                "item5",
                "item6",
                "item7",
                "item8",
                "item9",
            )
        )
    }

    var selectedItem:String? by remember {mutableStateOf(null)}

    ReorderContainer(
        state = reorderState,
        modifier = modifier,
    ) {
        Row {
            Column() {
                items.forEach{ item ->
                    Box(
                        modifier = Modifier
                            .padding(20.dp)
                            .background(MaterialTheme.colorScheme.primary)
                            .size(60.dp)
                    ){Text(item)}
                }
            }
            Column (
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items.forEach { item->
                    ReorderableItem(
                        state = reorderState,
                        key = item,
                        data = item,
                        onDrop = { state ->
                            println("onDragEnd")
                            selectedItem=null
                        },
                        onDragEnter = { state ->
//                            println("onDragEnter")
                            if(selectedItem==null){
                                selectedItem=item
                                println("onDragStart")
                            }
                            items = items.toMutableList().apply {
                                val index = indexOf(item)
                                remove(state.data)
                                add(index, state.data)
                            }
                        },
//                        onDragExit = { state -> selectedItem=item },
                        draggableContent = {
                            Box(
                                modifier = Modifier
                                    .padding(20.dp)
                                    .background(MaterialTheme.colorScheme.primaryContainer)
                                    .size(60.dp)
                            ){Text(selectedItem?:"")}
                        },
                        modifier = Modifier
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(20.dp)
                                .graphicsLayer {
                                    alpha = if (isDragging) 0f else 1f
                                }
                                .background(MaterialTheme.colorScheme.primary)
                                .size(60.dp)
                        ){Text(item)}
                    }
                }
            }
        }
    }
}