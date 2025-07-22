package net.systemvi.configurator.components.design

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mohamedrejeb.compose.dnd.reorder.ReorderContainer
import com.mohamedrejeb.compose.dnd.reorder.ReorderableItem
import com.mohamedrejeb.compose.dnd.reorder.rememberReorderState
import com.sun.javafx.fxml.expression.Expression.add
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import net.systemvi.configurator.components.configure.KeycapPosition
import net.systemvi.configurator.model.Keycap


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DragAndDropRows() {
    val columnState = rememberLazyListState()
    val reorderState = rememberReorderState<Color>()
    val scope = rememberCoroutineScope()
    val viewModel = viewModel { DesignPageViewModel() }
    var colors by remember {
        mutableStateOf(
            listOf(
                Color.Red,
                Color.Blue,
                Color.Yellow,
                Color.Green,
            )
        )
    }

    val keymap = viewModel.keymap
    var rows by remember { mutableStateOf(viewModel.keymap.keycaps) }

    ReorderContainer(
        state = reorderState,
        modifier = Modifier.size(300.dp)
    ) {
        LazyColumn(
            state = columnState,
        ) {
            items(colors, key = {it}) {color ->
                ReorderableItem(
                    state = reorderState,
                    key = color,
                    data = color,
                    onDrop = {},
                    onDragEnter = { it ->
                        colors = colors.toMutableList().apply {
                            val index = indexOf(color)
                            if (index == -1) return@ReorderableItem
                            remove(it.data)
                            add(index, it.data)


                        }
                    },
                    draggableContent = {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .background(
                                    color = color
                                )
                        ) {
                        }

                    }
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .background(
                                color = color
                            )
                    )
                }
            }
        }
    }
}

//suspend fun handleLazyListScroll(
//    lazyListState: LazyListState,
//    dropIndex: Int,
//): Unit = coroutineScope {
//    val firstVisibleItemIndex = lazyListState.firstVisibleItemIndex
//    val firstVisibleItemScrollOffset = lazyListState.firstVisibleItemScrollOffset
//
//    // Workaround to fix scroll issue when dragging the first item
//    if (dropIndex == 0 || dropIndex == 1) {
//        launch {
//            lazyListState.scrollToItem(firstVisibleItemIndex, firstVisibleItemScrollOffset)
//        }
//    }
//
//    // Animate scroll when entering the first or last item
//    val lastVisibleItemIndex =
//        lazyListState.firstVisibleItemIndex + lazyListState.layoutInfo.visibleItemsInfo.lastIndex
//
//    val firstVisibleItem = lazyListState.layoutInfo.visibleItemsInfo.firstOrNull() ?: return@coroutineScope
//    val scrollAmount = firstVisibleItem.size * 2f
//
//    if (dropIndex <= firstVisibleItemIndex + 1) {
//        launch {
//            lazyListState.animateScrollBy(-scrollAmount)
//        }
//    } else if (dropIndex == lastVisibleItemIndex) {
//        launch {
//            lazyListState.animateScrollBy(scrollAmount)
//        }
//    }
//}


