package net.systemvi.configurator.components.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.mohamedrejeb.compose.dnd.reorder.ReorderContainer
import com.mohamedrejeb.compose.dnd.reorder.ReorderableItem
import com.mohamedrejeb.compose.dnd.reorder.rememberReorderState

data class DropInfo(val dragStartIndex:Int,val dragEndIndex:Int)

@Composable
fun <T> DraggableColumn(items: List<T>,key:(T)->Any,onDrop:(dropInfo:DropInfo)->Unit,itemContent:@Composable (index:Int,item:T) -> Unit){

    val reorderState = rememberReorderState<T>()

    var draggableItems by remember { mutableStateOf(items) }

    LaunchedEffect(items){
        draggableItems = items
    }

    var selectedItem:T? by remember {mutableStateOf(null)}
    var startIndex by remember {mutableStateOf(0)}

    ReorderContainer(
        state = reorderState,
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
        ) {
            draggableItems.forEachIndexed { index,item->
                ReorderableItem(
                    state = reorderState,
                    key = key(item),
                    data = item,
                    onDrop = { state ->
                        println("onDragEnd")
                        onDrop(DropInfo(startIndex,index))
                        selectedItem=null
                    },
                    onDragEnter = { state ->
                        if(selectedItem==null){
                            startIndex = index
                            selectedItem=item
                            println("onDragStart")
                        }
                        draggableItems = draggableItems.toMutableList().apply {
                            val index = indexOf(item)
                            remove(state.data)
                            add(index, state.data)
                        }
                    },
                    draggableContent = {
                        itemContent(startIndex,item)
                    },
                    modifier = Modifier
                ) {
                    itemContent(index,item)
                }
            }
        }
    }
}