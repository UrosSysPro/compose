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
import arrow.optics.copy
import com.mohamedrejeb.compose.dnd.reorder.ReorderContainer
import com.mohamedrejeb.compose.dnd.reorder.ReorderableItem
import com.mohamedrejeb.compose.dnd.reorder.rememberReorderState

data class DropInfo(val dragStartIndex:Int,val dragEndIndex:Int)

@Composable
fun <T> DraggableColumn(items: List<T>,key:(T)->Any,onDrop:(dropInfo:DropInfo)->Unit,itemContent:@Composable (index:Int,item:T,isSelected:Boolean) -> Unit){

    val reorderState = rememberReorderState<T>()

    var draggableItems by remember { mutableStateOf(items.copy{} ) }

    LaunchedEffect(items){
        draggableItems = items.copy{}
    }

    var selectedItem:T? by remember {mutableStateOf(null)}
    var startIndex by remember {mutableStateOf(0)}

    LaunchedEffect(selectedItem){
        println((selectedItem as List<Any>?)?.size?:-1)
        println(startIndex)
    }

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
                        startIndex = 0
                    },
                    onDragEnter = { state ->
                        if(selectedItem==null){
                            startIndex = index
                            selectedItem = item
                            println("onDragStart")
                        }
                        draggableItems = draggableItems.toMutableList().apply {
                            val index = indexOf(item)
//                            if(index==-1)return@apply
                            remove(state.data)
                            add(index, state.data)
                        }
                    },
                    draggableContent = {
                        if(selectedItem!=null){
                            itemContent(startIndex,selectedItem!!,false)
                        }
                    },
                    modifier = Modifier
                ) {
                    itemContent(index,item,isDragging)
                }
            }
        }
    }
}