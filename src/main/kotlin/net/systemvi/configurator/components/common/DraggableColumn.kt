package net.systemvi.configurator.components.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.mohamedrejeb.compose.dnd.reorder.ReorderableItemScope
import com.mohamedrejeb.compose.dnd.reorder.rememberReorderState
import net.systemvi.configurator.model.Keycap

data class DropInfo(val dragStartIndex:Int,val dragEndIndex:Int)

enum class DraggableListDirection {
    vertical,
    horizontal,
}

typealias DraggableListItem<T> = @Composable (index:Int, item:T, isSelected:Boolean) -> Unit

@Composable
fun <T> DraggableList(items: List<T>, key:(T)->Any, onDrop:(dropInfo:DropInfo)->Unit,direction: DraggableListDirection=DraggableListDirection.vertical,itemContent: DraggableListItem<T>){

    val reorderState = rememberReorderState<T>()

    var draggableItems by remember { mutableStateOf(items.copy{} ) }

    LaunchedEffect(items){
        draggableItems = items.copy{}
    }

    var selectedItem:T? by remember {mutableStateOf(null)}
    var startIndex by remember {mutableStateOf(0)}

    @Composable
    fun childElements(){
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
                    draggableItems=items.copy{}
                },
                onDragEnter = { state ->
                    if(selectedItem==null){
                        startIndex = index
                        selectedItem = item
                        println("onDragStart")
                    }
                    draggableItems = draggableItems.toMutableList().apply {
                        val index = indexOf(item)
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

    ReorderContainer(
        state = reorderState,
    ) {
        when (direction) {
            DraggableListDirection.vertical ->
                Column (
                    modifier = Modifier
//                        .fillMaxSize()
                ) {
                    childElements()
                }
            DraggableListDirection.horizontal ->
                Row (
                    modifier = Modifier
//                        .fillMaxSize()
                ) {
                    childElements()
                }
        }
    }
}
