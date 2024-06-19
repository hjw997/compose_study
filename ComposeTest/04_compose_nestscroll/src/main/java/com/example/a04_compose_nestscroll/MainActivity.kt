package com.example.a04_compose_nestscroll

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.example.a04_compose_nestscroll.ui.theme.ComposeTestTheme


/**
 * 嵌套滚动
 * 嵌套滚动一直是我最不喜欢做的事情，但是架不住就是有很多朋友会问。
 *
 * RecyclerView是支持嵌套滚动的，但我认为绝大部分的情况下大家应该都用不到它。每当你认为自己需要用到嵌套滚动时，我觉得都应该先暂停一下，想想是不是有其他的替代方案，如ConcatAdapter等。
 *
 * 而到了Compose当中，这下好了，Lazy Layout压根就不支持嵌套滚动，这下直接就把大家的念象给断了。
 *
 * 那么我为什么还要写这个主题呢？因为Compose中还允许一些场景和逻辑都比较合理的嵌套滚动，我们主要来看这部分的用法。
 *
 * 首先第一种合理的嵌套滚动，就是内层和外层的列表滚动方向并不一致，这样它们之间是没有滑动事件冲突的，因此合情合理。示例代码如下
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
                    VerticalScrollable()
                }
            }
        }
    }
}

@Composable
fun VerticalScrollable() {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        HorizontalScrollView()
        for (i in 1..10) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(10.dp)
            ) {
                Text(
                    text = "Item $i",
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    modifier = Modifier.fillMaxSize()
                        .wrapContentHeight(Alignment.CenterVertically)

                )
            }
        }
    }
}

@Composable
fun HorizontalScrollView() {
    val list = ('A'..'Z').map { it.toString() }
    LazyRow(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
        items(list) {
            Card(modifier = Modifier
                .width(120.dp)
                .heightIn(200.dp)) {
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



