package com.example.a03_compose_lazy_layout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a03_compose_lazy_layout.ui.theme.ComposeTestTheme

/**
 * rememberLazyListState
 * 我们在使用RecyclerView编写滚动列表的时候，除了实现最基础的滚动功能之外，通常还会让程序随着列表的滚动进行一些额外事件的响应。如随着滚动隐藏和显示某些控件。
 *
 * 而如果想要在Lazy Layout中实现类似效果的话，则需要借助rememberLazyListState函数，我们接下来就瞧一瞧具体如何实现。
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainLayout()
                }
            }
        }
    }
}

@Composable
fun MainLayout() {
    //调用rememberLazyListState函数，将能够得到一个LazyListState对象。
    val state = rememberLazyListState()
    //我们可以通过访问它的firstVisibleItemIndex属性来得知当前第一个可见子项元素的下标。
    val firstVisibleItemIndex = state.firstVisibleItemIndex
    //还可以访问firstVisibleItemScrollOffset属性来得到当前第一个可见子项元素的偏移距离。
    val firstVisibleItemScrollOffset = state.firstVisibleItemScrollOffset

    Box {
        ScrollableList(state = state)
        val shouldShowAddButton = firstVisibleItemIndex == 0
        AddButton(isVisible = shouldShowAddButton)
    }
}

@Composable
fun ScrollableList(state: LazyListState) {
    val list = ('A'..'Z').map { it.toString() }

    LazyColumn(state = state) {
        items(list) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(10.dp)
            ) {
                Text(
                    text = it,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentHeight(Alignment.CenterVertically)
                )
            }
        }
    }
}

@Composable
fun BoxScope.AddButton(isVisible: Boolean) {
    if (isVisible) {
        FloatingActionButton(
            onClick = { /*TODO*/ },
            modifier = Modifier.align(Alignment.BottomEnd).padding(20.dp)
        ) {
            Icon(Icons.Filled.Add, "Add button")
        }
    }
}


