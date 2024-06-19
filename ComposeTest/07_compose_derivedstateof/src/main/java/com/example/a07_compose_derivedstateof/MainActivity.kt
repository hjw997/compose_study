package com.example.a07_compose_derivedstateof

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.a07_compose_derivedstateof.ui.theme.ComposeTestTheme

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
                    // MainLayout01()
                    // MainLayout02()
                    MainLayout03()
                }
            }
        }
    }
}

@Composable
fun MainLayout01() {
    var clickCount by remember {
        mutableStateOf(0)
    }
    Log.d("TAG", "MainLayout: 重组----11111")
    Column {
        Button(onClick = { clickCount++ }) {

            Text(text = "Add + 1 ")
        }
        if (clickCount > 5) {
            Text(text = "You clicked a lot")
        }
        //在这种场景下，当前代码就会导致大量的无效重组，没有任何的意义，只会浪费性能
        Log.d("TAG", "MainLayout: 重组----2222")
    }
}


/**
 * 使用 derivedStateOf
 * 那么上述代码中，clickCount就是一个State变量。因此，当这个变量的值发生变化时，所有读取这个值的Composable函数都会发生重组，以刷新界面。
 *
 * 根据这个特性，我们会发现，每当点击一下按钮，MainLayout函数都会发生一次重组。但实际上，只有在第5次点击按钮的时候，界面才会发生一次UI变动，其他时候UI都是不会变化的。在这种场景下，当前代码就会导致大量的无效重组，没有任何的意义，只会浪费性能。
 *
 * 那么如何解决这个问题呢？这就需要引入我们今天文章的主题了：derivedStateOf。
 *
 * derivedStateOf其实和mutableStateOf是比较相似的，它们都是用于创建State变量，然后Compose则会基于State变量值的变化来触发重组行为。
 *
 * 不同的是，derivedStateOf接收的一个表达式，只有当这个表达式中的条件发生变化了，那么才算是State的值发生了变化，这时才会触发重组。
 *
 * 我们通过具体的代码来看一下吧，如下所示：
 */
@Composable
fun MainLayout02() {
    var clickCount by remember {
        mutableStateOf(0)
    }

    /// derivedStateOf接收的一个表达式，只有当这个表达式中的条件发生变化了，那么才算是State的值发生了变化，这时才会触发重组。
    val clickedALot by remember {
        derivedStateOf {
            clickCount >= 5
        }
    }

    Log.d("TAG", "MainLayout: 重组----11111")
    Column {
        Button(onClick = { clickCount++ }) {

            Text(text = "Add + 1 ")
        }
        if (clickedALot) {
            Text(text = "You clicked a lot")
        }
        //在这种场景下，当前代码就会导致大量的无效重组，没有任何的意义，只会浪费性能
        Log.d("TAG", "MainLayout: 重组----2222")
    }
}

/**
 *
 */
@Preview
@Composable
fun MainLayout03() {
    /// 只要是依赖 state 的,state 值发生变化就会来重组.
    Log.d("TAG", "MainLayout03: ----Recompose")
    val state = rememberLazyListState()
    Box {
        ScrollableList(state)
        // Frequently changing state should not be directly read in composable function ✅ 代码提示
        // val isShowAddButton = state.firstVisibleItemIndex == 0
        /**
         * shouldShowAddButton变量用derivedStateOf包裹了进来，那么根据derivedStateOf的特性，
         * 只有当这个表达式中的条件发生变化了，才算是State的值发生了变化，这时才会触发重组。
         */

        val isShowAddBtton by remember {
            derivedStateOf { state.firstVisibleItemIndex == 0 }
        }
        AddButton(isFBBtnVisible = isShowAddBtton)
    }
}


@Composable
fun ScrollableList(lazyListState: LazyListState) {
    val list = ('A'..'Z').map { it.toString() }
    LazyColumn(state = lazyListState) {
        items(list) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(10.dp)
            ) {
                Text(
                    text = "Item $it",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentHeight(Alignment.CenterVertically)
                )
            }
        }
    }
}


@Composable
fun BoxScope.AddButton(isFBBtnVisible: Boolean) {
    if (isFBBtnVisible) {
        FloatingActionButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .size(60.dp)
                .align(Alignment.BottomEnd)
                .padding(15.dp)
        ) {
            Icon(Icons.Filled.AddCircle, "Add")
        }
    }

}
