package com.example.a02_compose_lazy_layout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a02_compose_lazy_layout.ui.theme.ComposeTestTheme

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
                    // ScrollableList00()
                    // ScrollableList01()
                    // ScrollableList02()
                    // ScrollableList03()
                    ScrollableList05()
                }
            }
        }
    }
}

/**
 * Lazy Layout:
 * LazyColumn 和 LazyRow
 *
 * LazyColumn和LazyRow
 * Lazy Layout只是一个可复用列表的统称，事实上并没有这样的一个控件。
 *
 * 我们需要根据不同的场景需求，采用与其所相对应的Compose控件。
 *
 * 比如上述例子中使用的LazyColumn，它就是用于在垂直方向上滚动的可复用列表。
 * 而LazyRow则是用于在水平方向上滚动的可复用列表。

 */
@Composable
fun ScrollableList00() {
    LazyColumn {
        items(20) { i ->
            Text(
                text = "Item $i",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            )
        }
    }
}

@Composable
fun ScrollableList01() {
    val  az = 'A'..'Z'
    val  list = az.map { it.toString() }
    LazyColumn {
        items(list) { letter ->
            Card(modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
            ) {
                Text(
                    text = letter,
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
fun ScrollableList02() {
    val  az = 'A'..'Z'
    val  list = az.map { it.toString() }

    LazyRow {
        items(list) {
            Card(modifier = Modifier
                .width(120.dp)
                .height(200.dp)) {
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

/**
 * 带索引的 Items 使用 itemsIndexed
 */
@Composable
fun ScrollableList03() {
    val list = ('A'..'Z').map { it.toString() }
    /// 有缺陷--滑动时候上面有未穿透的 区域 可通过 ScrollableList04  ScrollableList05 方法中的解决
    LazyColumn(modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)) {
        itemsIndexed(list){ index, item ->
            Card(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .height(80.dp)) {
                Text(
                    text = "$item + index = $index",
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
fun ScrollableList04() {
    val list = ('A'..'Z').map { it.toString() }
    //contentPadding 使用
    LazyColumn(contentPadding = PaddingValues(top = 10.dp, bottom = 10.dp)) {
        itemsIndexed(list){ index, item ->
            Card(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .height(80.dp)) {
                Text(
                    text = "$item + index = $index",
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
fun ScrollableList05() {
    val list = ('A'..'Z').map { it.toString() }

    LazyColumn(verticalArrangement = Arrangement.spacedBy(5.dp)) {
        itemsIndexed(list){ index, item ->
            Card(modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)) {
                Text(
                    text = "$item + index = $index",
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