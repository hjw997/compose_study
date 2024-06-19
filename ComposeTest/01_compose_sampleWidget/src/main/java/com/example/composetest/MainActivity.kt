package com.example.composetest

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composetest.ui.theme.ComposeTestTheme
import kotlin.math.roundToInt

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
                    // SimpleWidgetColumn()
                    // SimpleWidgetRow()
                    // SimpleWidgetBox()
                    // IconImage()
                    // PointerInputEvent()
                    // ClickableCompose()
                    //ScrollableCompose()
                    //DraggableCompose()
                    //DraggableXYCompose()
                    //ModifierSequenceCompose()
                    //ParentLayout()
                    //Counter()
//                    CallCounter(modifier = Modifier.wrapContentSize(
//                        align = Alignment.Center
//                    ))

                    // CallCounterVM()

                    SimpleWidgetTextField()


                }
            }
        }
    }
}


/**
 * 回顾 textField 输入的时候 没有响应 需要状态来解决
 *
 */

@Composable
fun SimpleWidgetTextField(modifier: Modifier = Modifier) {

    /// 本来属于 TextField 的状态 提升到这里.
    var userInput by rememberSaveable {
        mutableStateOf("")
    }

    Column {
        TextFieldCompose(text = userInput, textChange = { new ->
            Log.d("TAG",new)
            userInput = new
        })
    }
}

@Composable
fun TextFieldCompose(text:String , textChange:(newValue : String ) -> Unit,modifier: Modifier = Modifier) {

    /// 状态提升
    TextField(value = text, onValueChange = {
        textChange(it)
    })
}

/**------------使用 ViewModel-----------------*/
@Composable
fun CallCounterVM(modifier: Modifier = Modifier,viewModel: MainViewModel = viewModel() ) {
    //我们需要将LiveData转换成State才行，observeAsState()函数就是用来做这个事情的，参数中传入的0表示它的初始值。
    val count by viewModel.count.observeAsState(0)
    val doubleCount by viewModel.doubleCount.observeAsState(0)
    Column {
        Counter(
            count = count,
            onIncrement = { viewModel.incrementCount() },
            btnTitle = "点击 + 1",
            modifier = modifier.wrapContentSize()
        )
        Counter(
            count = doubleCount,
            btnTitle = "点击 + 2 ",
            onIncrement = { viewModel.incrementDoubleCount() },
            modifier = modifier.wrapContentSize()
        )
    }
}


/**-----------------------------*/
/** 状态提升 03:
 * Compose提供了一种编程模式，叫State hoisting，中文译作状态提升。
 * UK/hɔɪst/ US/hɔɪst/
 * 也就是说，我们要尽可能地把State提到更上一层的Composable函数当中，
 * 这样偏底层的Composable函数就可以成为无状态的函数，从而提高它们的复用性。
 *
 * 而实现状态提升最核心的步骤只有两个。
 * 第一就是将原来声明State对象的写法改成用参数传递的写法，就像上面的示例一样。
 *
 * 第二就是将写入State数据的地方改成用回调的方式来通知到上一层。
 * Kotlin语言可以借助高阶函数来轻松实现回调编写
 * 还不了解的朋友们请参考《第一行代码 第3版》6.5节。
 *
 * 下面我们通过实践的方式来对刚才的Counter函数进行状态提升。
 */

@Composable
fun CallCounter(modifier: Modifier = Modifier) {
    //rememberSaveable 屏幕旋转都会保存
    var count by rememberSaveable {
        mutableStateOf(0)
    }
    /// 无状态后 复用性更加和出色 比如 点击 + 2
    var doubleCount by rememberSaveable {
        mutableStateOf(0)
    }
    Column(modifier = modifier) {
        Counter(
            count = count,
            btnTitle = "点击 + 1 ",
            onIncrement = {
                count++
            },
            modifier = modifier
        )

        Counter(
            count = doubleCount,
            btnTitle = "点击 + 2 ",
            onIncrement = {
                doubleCount += 2
            },
            modifier = Modifier
        )
    }



}

/**
 * 通常意义上来讲，像这种状态向下传递、事件向上传递的编程模式，
 * 我们称之为单向数据流模式（Unidirectional( [ˌjuːnɪdɪˈrekʃənəl; ˌjuːnɪdaɪˈrekʃənəl] adj:单向的) Data Flow）。
 * 而状态提升就是这种单向数据流模式在Compose中的具体应用。
 *
 * 关于状态提升最后还有一个问题。
 * 既然我们可以将状态提升到上一层，那么是不是还可以再往上提一层，再往上呢？提到哪一层才能算结束？
 * 关于这个问题其实并没有一个非常精准的答案，基本上只要你想往上提，提多少层都是可以的，因此更多是根据你实际的业务需求来进行状态提升。
 * 不过虽然状态提升没有上限，下限却是有的，如果你的状态提升的层级不够高，那么你的代码将很难满足单向数据流的编程模式。
 *
 * 以下是你应该考虑的状态提升最少应该到达哪个层级的关键因素：
 *
 * 如果有多个Composable函数需要读取同一个State对象，那么至少要将State提升到这些Composable函数共有的父级函数当中。
 * 如果有多个Composable函数需要对同一个State对象进行写入，那么至少要将State提升到所有执行写入的Composable函数里调用层级最高的那一层。
 * 如果某个事件的触发会导致两个或更多的State发生变更，那么这些State都应该提升到相同的层级。
 * 只要你在编写Compose代码的时候始终将这种编程模式牢记心头，那么最终你的代码质量一定会非常不错的。
 *
 *
 */

@Composable
fun Counter(count: Int, btnTitle:String,  onIncrement: () -> Unit,modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "$count",
            fontSize = 50.sp
        )
        Button(onClick = {
            onIncrement()
        }) {
            Text(
                text = btnTitle,
                fontSize = 20.sp
            )
        }
    }
}


/**
 * State 进阶02 :
 * 写的好和用的好对比
 * 有状态和无状态:
 */
@Composable
fun StatefulCounter(modifier: Modifier = Modifier) {
    //根据Google给出的最佳实践准则，有状态的Composable函数通常在复用性和可测试性方面都会表现得比较差。
    val count by remember {
        mutableStateOf(0)
    }
    Text(text = "$count", fontSize = 30.sp)
}

@Composable
fun StatelessCounter(count: Int, modifier: Modifier = Modifier) {
    //当我们编写Composable函数时，最理想的情况就是尽可能地让它成为一个无状态的Composable函数。
    Text(text = "$count", fontSize = 35.sp)
}



/**
 * State 基础讲解01:
 */
@Composable
fun Counter(modifier: Modifier = Modifier) {
    //事实上，remember 和 mutableStateOf 在Composable函数中几乎永远都是配套使用的。
    val count = remember {
        mutableStateOf(0)
    }

    /**
     *   借助 kotlin 的委托语法对 State用法进一步简化
     *   别看只是改变了一下赋值方式，count变量的类型都会因此而发生变化。之前用等号赋值的时候，
     *   count的类型是MutableState，而改用by关键字赋值之后，count的类型就变成了Int。
     */
    var count01 by remember {
        mutableIntStateOf(0)
    }

    /**
     * rememberSaveable函数是remember函数的一个增强版，
     * 它唯一和remember不同的地方就是在于其包裹的数据在手机横竖屏旋转时会被保留下来。
     */
    val count02 = rememberSaveable { mutableStateOf(0) }

    Column(modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "${count.value}",
            fontSize = 50.sp,
            color = Color.Green
        )

        Text(
            text = "$count01", /// 不用再 通过 .value 了
            fontSize = 45.sp,
            color = Color.Red
        )

        Text(
            text = "${count02.value}", ///
            fontSize = 45.sp,
            color = Color.Blue
        )

        Button(onClick = {
            count.value++
            count01 ++
            count02.value++
        }) {
            Text(text = "点击 + 1 ", fontSize = 25.sp)
        }
    }
}

/**-------------------------------**/


@Composable
fun IconImage() {
    Image(
        painter = painterResource(id = R.drawable.baseline_pets_24),
        contentDescription = "Icon Image",
        modifier = Modifier
            .wrapContentSize(align = Alignment.CenterStart)
            .border(1.dp, Color.Blue, CircleShape)
            .clip(CircleShape)
            .rotate(180f)
    )
}

@Composable
fun PointerInputEvent() {
    Box(
        modifier = Modifier
            .requiredSize(200.dp)
            .background(Color.Blue)
            .pointerInput(Unit) {
//                awaitPointerEventScope {
//                    while (true) {
//                        val event = awaitPointerEvent()
//                        Log.d("PT", "event:${event.type} ")
//                    }
//                }

                detectTapGestures {
                    Log.d("PT", "Tap-offset:${it} ")
                }

            }
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    Log.d("PT", "Drag-change:${change} , dragAmount:$dragAmount ")
                }
            }
    )
}

/**
 * 控件点击
 */
@Composable
fun ClickableCompose() {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .requiredSize(200.dp)
            .background(Color.Green)
            .clickable {
                Toast
                    .makeText(context, "BoxClicked", Toast.LENGTH_LONG)
                    .show()
            }
    )

}

/**
 * 让控件滚动
 */
@Composable
fun ScrollableCompose() {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .requiredSize(200.dp)
            .background(Color.Cyan)
            .verticalScroll(rememberScrollState())
    ) {
        repeat(10) {
            Text(
                text = "Item $it",
                color = Color.White,
                fontSize = 20.sp
            )
        }
    }
}

/**
 * 控件拖拽--只能一个方向上拖拽 弊端.
 */
@Composable
fun DraggableCompose() {
   var offsetX by remember { mutableStateOf(0f) }
    Box(modifier = Modifier
        .offset { IntOffset(offsetX.roundToInt(), 0) }
        .requiredSize(200.dp)
        .background(Color.Red)
        .draggable(
            orientation = Orientation.Horizontal,
            state = rememberDraggableState { delta ->
                offsetX += delta
            }
        )
    )
}

/**
 * xy 方向上拖拽
 */
@Composable
fun DraggableXYCompose() {
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    Box(modifier = Modifier
        .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
        .requiredSize(200.dp)
        .background(Color.Red)
        .pointerInput(UInt) {
            detectDragGestures { change, dragAmount ->
                //由于这是底层API，所以很多事情要自己做，比如事件处理完了，要记得调用consume()函数将它消费掉。
                change.consume()
                offsetX += dragAmount.x
                offsetY += dragAmount.y
            }
        }

    )
}

/**
 * 作用域 代码推荐
 */
@Composable
fun LinearVerticalLayout() {
    Column {
        Text(text = "Hello", modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
    }
}

/**
 * 作用域 代码推荐
 */
@Composable
fun LinearHorizontalLayout() {
    Row {
        Text(text = "Hello", modifier = Modifier.align(alignment = Alignment.CenterVertically))
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello-- $name!",
        modifier = modifier
    )
}

/**
 * modifier 不同顺序的影响
 */
@Composable
fun ModifierSequenceCompose() {
    Image(
        painter = painterResource(id = R.drawable.baseline_pets_24),
        contentDescription = "Pet",
        modifier = Modifier
            .wrapContentSize()
            .background(Color.Gray)
            .padding(10.dp) //margin 相当与
            .border(1.dp, Color.Magenta, CircleShape)
            .padding(20.dp) // padding
            .clip(CircleShape)
    )
}

/**
 * 规范: 每一个 Composable 函数都要有一个 Modifier
 */
@Composable
fun TestComposeable(a: Int, b: Int, modifier: Modifier = Modifier) {

}

@Composable
fun ParentLayout(modifier: Modifier = Modifier) {
    Column {
        IconImage(Modifier.align(Alignment.CenterHorizontally))
        IconImage(Modifier.align(Alignment.Start))
    }
}

@Composable
fun IconImage(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.baseline_pets_24),
        contentDescription = "A Image",
        modifier = modifier
            .wrapContentSize()
            .background(Color.Yellow)
            .padding(10.dp)
            .border(2.dp, Color.Blue, shape = CircleShape)
            .padding(5.dp)
            .clip(CircleShape)
    )
}

/**-------------------------------**/

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeTestTheme {
        // Greeting("Android")
        // SimpleWidgetColumn()

    }
}

/**笔记:
 * Composable函数还有一个约定俗成的习惯，就是函数的命名首字母需要大写
 * Composable函数名才特意要求首字母需要大写，这样我们就能够更加直观地通过函数名称来快速地判断一个函数是不是Composable函数
 */

@Composable
fun SimpleWidgetColumn() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            modifier = Modifier.align(Alignment.Start),
            text = "this is Text",
            color = Color.Blue,
            fontSize = 10.sp
        )
        val context = LocalContext.current

        Button(onClick = { Toast.makeText(context, "This is toast", Toast.LENGTH_LONG).show() })
        {
            Text(
                text = "This is Button",
                color = Color.Red,
                fontSize = 20.sp
            )
        }

        TextField(
            value = "",
            onValueChange = {},
            placeholder = {
                Text(text = "Type something here")
            }
        )
        Image(
            painter = painterResource(id = R.drawable.baseline_pets_24),
            contentDescription = "A dog Image"
        )

        // val bitMap = ImageBitmap.imageResource(id = R.drawable.baseline_pets_24)
        //Image(bitmap = bitMap, contentDescription = "Agod Image")

//        AsyncImage(
//            model = "https://img-blog.csdnimg.cn/20200401094829557.jpg",
//            contentDescription = "First line of code"
//        )

        CircularProgressIndicator(
            color = Color.Green,
            strokeWidth = 6.dp
        )

        LinearProgressIndicator()

        LinearProgressIndicator(color = Color.Blue, trackColor = Color.Cyan)
    }
}

@Composable
fun SimpleWidgetRow() {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,

    ) {
        Text(
            modifier = Modifier.align(Alignment.Bottom),
            text = "this is Text",
            color = Color.Blue,
            fontSize = 10.sp
        )
        val context = LocalContext.current

        Button(onClick = { Toast.makeText(context, "This is toast", Toast.LENGTH_LONG).show() })
        {
            Text(
                text = "This is Button",
                color = Color.Red,
                fontSize = 20.sp
            )
        }

        TextField(
            value = "",
            onValueChange = {},
            placeholder = {
                Text(text = "Type something here")
            }
        )
        Image(
            painter = painterResource(id = R.drawable.baseline_pets_24),
            contentDescription = "A dog Image"
        )

        // val bitMap = ImageBitmap.imageResource(id = R.drawable.baseline_pets_24)
        //Image(bitmap = bitMap, contentDescription = "Agod Image")

//        AsyncImage(
//            model = "https://img-blog.csdnimg.cn/20200401094829557.jpg",
//            contentDescription = "First line of code"
//        )

        CircularProgressIndicator(
            color = Color.Green,
            strokeWidth = 6.dp
        )

        LinearProgressIndicator()

        LinearProgressIndicator(color = Color.Blue, trackColor = Color.Cyan)


    }
}

@Composable
fun SimpleWidgetBox() {
    Box {
        Text(
            modifier = Modifier.align(Alignment.TopEnd),
            text = "this is Text",
            color = Color.Blue,
            fontSize = 10.sp
        )
        val context = LocalContext.current

        Button(onClick = { Toast.makeText(context, "This is toast", Toast.LENGTH_LONG).show() })
        {
            Text(
                text = "This is Button",
                color = Color.Red,
                fontSize = 20.sp
            )
        }

        TextField(
            modifier = Modifier.align(Alignment.BottomCenter),
            value = "",
            onValueChange = {},
            placeholder = {
                Text(text = "Type something here")
            }
        )
        Image(
            painter = painterResource(id = R.drawable.baseline_pets_24),
            contentDescription = "A dog Image"
        )

        // val bitMap = ImageBitmap.imageResource(id = R.drawable.baseline_pets_24)
        //Image(bitmap = bitMap, contentDescription = "Agod Image")

//        AsyncImage(
//            model = "https://img-blog.csdnimg.cn/20200401094829557.jpg",
//            contentDescription = "First line of code"
//        )

        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.CenterStart),
            color = Color.Green,
            strokeWidth = 6.dp
        )

        LinearProgressIndicator()

        LinearProgressIndicator(color = Color.Blue, trackColor = Color.Cyan)


    }
}




