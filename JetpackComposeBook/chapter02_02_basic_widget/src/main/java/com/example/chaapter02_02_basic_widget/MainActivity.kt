package com.example.chaapter02_02_basic_widget

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ModifierInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chaapter02_02_basic_widget.ui.theme.JetpackComposeBookTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeBookTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //TextDemo01("Android")
                    //TextFieldDemo_02()
                    //BSiteSearchBar()
                    //ButtonSample01()
                    //ButtonSample02()
                    IconButtonSample()

                }
            }
        }
    }
}

/**
 * IconButton 组件实际上只是Button组件的简单封装（一个可点击的图标）
 * 一般来说，我们需要在IconButton组件里提供一个图标组件，这个图标的默认的大小一般为 24 x 24 dp.
 */
@Composable
fun IconButtonSample() {
    var collection by remember {
        mutableStateOf(false)
    }
    Column {
        IconButton(onClick = {
            collection = !collection
        }) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = "favorite",
                tint = if (collection) Color.Red else Color.Gray
            )
        }
    }
}

/**
 * Button 的 interactionSource 相当于 Selector
 * PS：Button 并非唯一可点击的组件，
 * 理论上任何的Composable 组件都可以通过 Modifier.clickable修饰符化身为可点击组件
 * 而Button 被点击后，需要额外进行一些事件相应处理，比如 Material Design 显示水波纹
 * 这些都是其内部通过拦截 Modifier.clickable 事件实现处理。
 * Modifier.clickable 已经被内部实现所占用。Button 需要提供单独的 onClick参数供开发和使用。
 * ⚠️：Button 的 onClick 在底层是通过覆盖 Modifier.clickable 实现的，
 *      所以不要为Button 设置Modifier.clickable,即使设置了，也会因为被onClick覆盖而没有任何效果。
 */
@Composable
fun ButtonSample02() {
    val interaction = remember {
        MutableInteractionSource()
    }
    val pressState = interaction.collectIsPressedAsState() //判断按钮是否按下
    val borderColor = if (pressState.value) Color.Green else Color.White

    ///还有其他的一些状态：
    interaction.collectIsFocusedAsState() //判断是否获取焦点的状态
    interaction.collectIsDraggedAsState() //判断是否是拖拽

    Column {
        Button(
            onClick = { /*TODO*/ },
            border = BorderStroke(2.dp, borderColor),
            interactionSource = interaction,
        ) {
            Text(text = "长按试试")
        }
    }
}

@Composable
fun ButtonSample01() {
    Column {
        /// 某一行： 代码向上移动 shift + opt + 上下箭头
        /// 某个组件上下 移动 shift + cmd + 上下箭头。
        Button(onClick = { /*TODO*/ }) {
            Text(text = "确认")
        }
        Button(onClick = { /*TODO*/ }) {
            //PS:Button 的 content 作用域是 RowScope 那么相当于这里面是个Row
            Icon(
                imageVector = Icons.Filled.Done,
                contentDescription = "",
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
            Text(text = "确认")
        }


    }
}

/**
 * 模仿一个 B站 搜索框:
 */
@Composable
fun BSiteSearchBar() {
    var text by remember {
        mutableStateOf("")
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFD3D3D3)),
        contentAlignment = Alignment.Center//将Box里面的组件放置于 Box 容器的中央.
    ) {
        BasicTextField(
            value = text,
            onValueChange = {
                text = it
            },
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 2.dp, horizontal = 8.dp)
                ) {
                    Icon(imageVector = Icons.Filled.Search, contentDescription = "Search Icon")
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .weight(1f), ///沾满剩余空间
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (text.isEmpty()) {
                            Text(
                                text = "输入点东西看看吧~",
                                style = TextStyle(color = Color(0, 0, 0, 128))
                            )
                        }
                        innerTextField()
                    }
                    if (text.isNotEmpty()) {
                        IconButton(
                            onClick = { text = "" },
                            modifier = Modifier
                                .padding(end = 20.dp)
                                .size(16.dp)
                                .border(2.dp, color = Color.Red, shape = RectangleShape)
                        ) {
                            Image(
                                imageVector = Icons.Filled.Clear,
                                contentDescription = "Clear Icon"
                            )
                        }
                    }
                }

            },
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .background(Color.White, CircleShape)
                .fillMaxWidth()
                .height(30.dp)

        )
    }

}

@Composable
fun TextFieldDemo_02() {
    var userName by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    Column {
        TextField(
            value = userName,
            label = { Text(text = "用户名") },
            onValueChange = {
                userName = it
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.AccountBox,
                    contentDescription = stringResource(id = R.string.app_name)
                )
            }
        )

        Spacer(modifier = Modifier.height(5.dp))

        TextField(
            value = password,
            label = { Text(text = "密码") },
            onValueChange = {
                password = it
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.ExitToApp,
                    contentDescription = null
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = {

                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.visibility),
                        contentDescription = ""
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(5.dp))

        var text by remember {
            mutableStateOf("")
        }
        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it
            },
            label = { Text(text = "用户名:") }
        )
        Spacer(modifier = Modifier.height(30.dp))

        /// TextField 和 OutlinedTextField 输入框高度是无法直接修改的.
        TextField(
            value = userName,
            label = { Text(text = "用户名") },
            onValueChange = {
                userName = it
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.AccountBox,
                    contentDescription = stringResource(id = R.string.app_name)
                )
            },
            modifier = Modifier.height(30.dp)
        )

        var basicText by remember {
            mutableStateOf("")
        }
        Spacer(modifier = Modifier.height(30.dp))
        /// BasicTextField 可以支持 大多数的定制需求.
        BasicTextField(
            value = basicText,
            onValueChange = {
                basicText = it
            },
            decorationBox = { innerTextField ->
                Column(
                    modifier = Modifier
                        .height(44.dp)
                        .border(border = BorderStroke(1.dp, Color.Yellow), shape = RectangleShape)
                ) {
                    innerTextField()
                    Divider(
                        thickness = 2.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Cyan)
                    )
                }

            }

        )


    }
}


@Composable
fun TextDemo01(name: String, modifier: Modifier = Modifier) {
    Column {
        Text(text = "Hello World")
        Text(text = stringResource(id = R.string.app_name))
        Text(text = "Hello World\n" + "GoodBy World")
        Text(
            text = "Hello $name!",
            modifier = modifier,
            color = Color.Red,
            fontSize = 40.sp,
            style = TextStyle(
                fontSize = 10.sp,
                fontWeight = FontWeight.Thin,
                background = Color.Cyan,
            )
        )

        Text(
            text = "Hello ",
            letterSpacing = 4.sp, //字间距
            style = TextStyle(
                textDecoration = TextDecoration.Underline,//下划线
            ),
            onTextLayout = {
                Log.d("TAG", "TextDemo01: $it")
            },//文本变化的回调

        )
        Text(
            text = "Hello Word001",
            style = MaterialTheme.typography.headlineLarge.copy(fontStyle = FontStyle.Italic)
        )
        Text(
            text = "1. 你好世界,我正在使用 Jetpack Compose 框架来开发我的App界面",
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            text = "2. 你好世界,我正在使用 Jetpack Compose 框架来开发我的App界面",
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
        )
        Text(
            text = "3. 你好世界,我正在使用 Jetpack Compose 框架来开发我的App界面",
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        //fontFamily设置:
        Text(
            text = "4. 你好世界,我正在使用 Jetpack Compose",
            style = MaterialTheme.typography.bodySmall,
            fontFamily = FontFamily.Monospace
        )
        Text(
            text = "5. 你好世界,我正在使用 Jetpack Compose",
            style = MaterialTheme.typography.labelMedium,
            fontFamily = FontFamily.Cursive
        )
        ///如果系统没有字体时候,可以点击 res 文件夹 创建 font 字体文件夹,然后将字体拖入文件夹即可.


        //AnnotatedString --多样式文字.
        Text(text = buildAnnotatedString {
            withStyle(style = SpanStyle(fontSize = 24.sp)) {
                append("你现在学习的章节是:")
            }
            withStyle(style = SpanStyle(fontWeight = FontWeight.W900, fontSize = 24.sp)) {
                append("Text组件")
            }
            append("\n")
            withStyle(style = ParagraphStyle(lineHeight = 25.sp)) {
                append("在刚刚讲过的内容中, 我们学会了,如何应用文字样式,以及如何限制文本的行数和处理溢出的视觉效果.")
                append("\n")

            }
            append("现在,我们在学习")
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.W900,
                    textDecoration = TextDecoration.Underline,
                    color = Color(0xFF59A869)
                )
            ) {
                append("AnnotatedString")
            }
        })

        val annotatedText = buildAnnotatedString {
            append("学习网站:")
            pushStringAnnotation(
                tag = "URL",
                annotation = "https://jetpackcompose.cn/docs/elements/text"
            )
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.W900,
                    textDecoration = TextDecoration.Underline,
                    color = Color.Green
                )
            ) {
                append("AnnotatedString")
            }
            pop()
        }

        ClickableText(text = annotatedText) { offset ->
            annotatedText.getStringAnnotations(
                tag = "URL",
                start = offset,
                end = offset
            ).firstOrNull()?.let { annotation ->
                Log.d("TAG", "TextDemo01: ${annotation.item}")
            }
        }

        SelectionContainer {
            Text(text = "我是可以被复制的文字")
        }
    }
}