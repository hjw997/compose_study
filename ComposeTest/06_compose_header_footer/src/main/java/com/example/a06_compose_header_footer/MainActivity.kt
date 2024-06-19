package com.example.a06_compose_header_footer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a06_compose_header_footer.ui.theme.ComposeTestTheme

/**
 * 拼接不同类型子项
 * 刚才有提到，RecyclerView中一些不合理的嵌套滚动需求其实可以考虑使用ConcatAdapter来解决。
 *
 * ConcatAdapter是用于将不同类型的子项元素拼接到一起，让它们形成一个整体可滚动的列表。由于这是Compose专场，我不会对ConcatAdapter的用法做更详细的讲解，还不了解的朋友可以参考这篇文章。
 *  https://blog.csdn.net/guolin_blog/article/details/105606409
 *
 * 那么Lazy Layout中是否也可以实现与ConcatAdapter类似的效果呢？答案是肯定的，而且更加简单。
 *
 * 目前我们已经知道，可以在Lazy Layout中添加一个items函数来指定要滚动的数据源列表。你当然也可以添加多个items函数来指定不同类型的数据源列表，这样就可以将不同类型的子项元素拼接到一起了。
 *
 * 除此之外，还可以在Lazy Layout中添加item函数来指定单个数据项，最终它们都会形成一个整体可滚动的列表。
 *
 * 下面我们来看一段代码示例：
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
                    ScrollableList()
                }
            }
        }
    }
}


@Composable
fun ImageHeader() {
    Image(painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = "", modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight())
}

@Composable
fun ImageFooter() {
    Image(painterResource(id = R.drawable.baseline_pets_24), contentDescription = "", modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight())
}

@Composable
fun ScrollableList() {
    val list = (1..10).map { it.toString() }
    LazyColumn {
        item {
            ImageHeader()
        }
        /// 给每个 子项的 item 添加 key 提升性能.
        items(list,key = {it}) {
            Card(modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(10.dp)) {
                Text(
                    text = it,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentHeight(Alignment.CenterVertically)
                )
            }
        }
        item {
            ImageFooter()
        }
    }
}



