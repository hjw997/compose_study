package com.example.a05_compose_nestscrollvertical

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.a05_compose_nestscrollvertical.ui.theme.ComposeTestTheme

/**
 * 第二种合理的嵌套滚动，即使内层和外层的列表滚动方向一致，只要内层列表在滚动方向上的尺寸是固定的，
 * 那么Compose对此仍然是支持的。
 *
 * 也就是说， 如果是纵向嵌套滚动，那么内层列表的高度必须是固定的。
 *          如果是横向嵌套滚动，那么内层列表的宽度必须是固定的。示例代码如下
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

    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {

        SubVerticalScrollable()

        for (i in 1..10) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)

            ) {
                Text(
                    text = "Item $i",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentHeight(align = Alignment.CenterVertically)

                )

            }
        }
    }
}


@Composable
fun SubVerticalScrollable() {
    val list = ('A'..'Z').map { it.toString() }
    /// 这个高度一定要固定 才可以嵌套滑动不冲突.
    LazyColumn(modifier = Modifier.height(300.dp)) {
        items(list) {
            Card(modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
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

