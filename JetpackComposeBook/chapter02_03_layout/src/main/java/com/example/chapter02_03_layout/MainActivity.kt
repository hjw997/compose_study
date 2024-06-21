package com.example.chapter02_03_layout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.chapter02_03_layout.ui.theme.JetpackComposeBookTheme


/**
 * 本模块学习 常用的布局组件:
 */
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
                    // ColumnSample()
                    // RowSample()
                    // BoxSample()
                    // SurfaceSample()
                    // SpacerSample()
                    ConstraintSample()
                }
            }
        }
    }
}

/**
 * 约束布局:
 * PS : 要在 build.gradle(app) 脚本中添加 Compose 版本的 ConstraintLayout
 * androidx.constraintlayout:constraintlayout-compose
 */
@Composable
fun ConstraintSample() {
//    val portImageRef = remember {
//        createRef() //❌:要在 ConstraintLayoutScope作用域中.
//    }
    Column {

        ConstraintLayout(
            modifier = Modifier
                .padding(top = 50.dp)
                .size(300.dp)
                .background(Color.Blue)
        ) {
            val portImageRef = remember {
                ///PS : createRef 要在 其作用域中才能生效.
                createRef()
            }
            Image(
                painter = painterResource(id = R.drawable.pic),
                contentDescription = null,
                modifier = Modifier.constrainAs(portImageRef) {
                    ///添加布局约束
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)

                })
        }

        HeightSpacer(value = 10.dp)
        ConstraintLayout(
            modifier = Modifier
                .width(300.dp)
                .height(100.dp)
        ) {

            val (portraitImageRef, userNameTextRef, desTextRef) = remember {
                createRefs()
            }
            Image(
                painter = painterResource(id = R.drawable.pic),
                contentDescription = null,
                modifier = Modifier.constrainAs(portraitImageRef) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                })

            Text(
                text = "Compose 技术爱好者",
                fontSize = 16.sp,
                maxLines = 1,
                textAlign = TextAlign.Left,
                modifier = Modifier.constrainAs(userNameTextRef) {
                    top.linkTo(portraitImageRef.top)
                    start.linkTo(portraitImageRef.end,10.dp)
                })

            Text(
                text = "我的个人描述......",
                fontSize = 14.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Light,
                modifier = Modifier.constrainAs(desTextRef) {

                    top.linkTo(userNameTextRef.bottom, 5.dp)
                    start.linkTo(portraitImageRef.end,10.dp)
                })


        }

        HeightSpacer(value = 10.dp)
        ConstraintLayout(
            modifier = Modifier
                .width(300.dp)
                .height(100.dp)
                .background(Color.Cyan)
        ) {

            val (portraitImageRef, userNameTextRef, desTextRef) = remember {
                createRefs()
            }
            Image(
                painter = painterResource(id = R.drawable.pic),
                contentDescription = null,
                modifier = Modifier.constrainAs(portraitImageRef) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                })

            Text(
                text = "一个名字特别特别特别特别特别特别特别长的用户名",
                fontSize = 16.sp,
//                maxLines = 2,
//                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Left,
                modifier = Modifier.constrainAs(userNameTextRef) {
                    top.linkTo(portraitImageRef.top)
                    start.linkTo(portraitImageRef.end,10.dp)
                    end.linkTo(parent.end, 10.dp)
                    //preferred 推断:
                    width = Dimension.preferredWrapContent
                }.background(Color.Blue))

            Text(
                text = "我的个人描述......",
                fontSize = 14.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Light,
                modifier = Modifier.constrainAs(desTextRef) {

                    top.linkTo(userNameTextRef.bottom, 5.dp)
                    start.linkTo(portraitImageRef.end,10.dp)
                })


        }
    }


}


/**
 * 可以对 Spacer 封装:
 */
@Composable
fun WidthSpacer(value: Dp) = Spacer(modifier = Modifier.width(value))

@Composable
fun HeightSpacer(value: Dp) = Spacer(modifier = Modifier.height(value))


/**
 * Spacer 留白
 */
@Composable
fun SpacerSample() {
    Box {

        Column {

            Row {
                Box(
                    Modifier
                        .size(100.dp)
                        .background(Color.Red)
                )

                Spacer(modifier = Modifier.width(20.dp)) //也可以用 Modifier.padding(horizontal=xx.dp)

                Box(
                    Modifier
                        .size(100.dp)
                        .background(Color.Magenta)
                )

                Spacer(modifier = Modifier.weight(1f)) //Row 剩下的全部由它占据

                Box(
                    Modifier
                        .size(100.dp)
                        .background(Color.Blue)
                )

            }
            HeightSpacer(value = 10.dp)

            Row {
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "按钮")
                }
                WidthSpacer(value = 10.dp)
                Text(text = "hahah")
            }
        }
    }
}

/**
 * Surface :平面,可以将很多的组件摆放到这个平面上,可以设置这个平面的边框,圆角, 阴影等.
 * surface 和 box 对比:
 * surface 如果想快速设置界面的形状,阴影,边框,颜色等,则用 Surface 更合适.
 */
@Composable
fun SurfaceSample() {
    Box(modifier = Modifier.padding(20.dp)) {

        Surface(
            shape = RoundedCornerShape(8.dp),
            shadowElevation = 10.dp,
            modifier = Modifier
                .width(300.dp)
                .height(100.dp)
        ) {
            Row(Modifier.clickable {

            }) {
                Image(
                    painter = painterResource(id = R.drawable.pic),
                    contentDescription = null,
                    modifier = Modifier.size(100.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.padding(horizontal = 12.dp))
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "Liratie", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.padding(vertical = 3.dp))
                    Text(text = "失礼了")
                }
            }
        }
    }
}


/**
 * Box 是 = FrameLayout:帧布局
 * 依次堆叠到 左上角 默认.
 */
@Composable
fun BoxSample() {
    Box {
        Box(
            modifier = Modifier
                .size(150.dp)
                .background(Color.Gray)
        )
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(Color.Red)
        ) {
            Text(text = "Box")
        }
    }
}

@Composable
fun RowSample() {
    Surface(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        shadowElevation = 8.dp,
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = "Jetpack Compose 是什么", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.padding(vertical = 5.dp))
            Text(text = " Jetpack Compose 是一款新型工具包，旨在帮助简化界面开发。该工具包将响应式编程模型与简洁易用的Kotlin 编程语言相结合，并采用完全声明式的代码 ")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween ///子项整体的水平排布方式.
            ) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Filled.Favorite, null)
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Filled.Menu, null)
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Filled.Share, null)
                }

            }
        }
    }
}


@Composable
fun ColumnSample() {
    Box {
        Column(
            modifier = Modifier
                .border(1.dp, color = Color.Green)
                .size(300.dp),//没有大小默认包裹内容
            /**
             * 指定了大小 才能使用 verticalArrangement 对子项做整体的设置
             */
            /**
             * 指定了大小 才能使用 verticalArrangement 对子项做整体的设置
             */
            /**
             * 指定了大小 才能使用 verticalArrangement 对子项做整体的设置
             */
            /**
             * 指定了大小 才能使用 verticalArrangement 对子项做整体的设置
             */
            verticalArrangement = Arrangement.Center,
        ) {
            Text(text = "AAAAAA", style = MaterialTheme.typography.bodyLarge)
            /**
             * Column 设置了大小后, 可以给子项通过 Modifier.align 来独立设置子项的对齐方式.
             * 对于垂直布局中的子项,Modifier.align 只能设置自己在水平方向的位置.
             * 对于水平布局中子项,Modifier.align 只能设置自己在垂直方向的位置.
             */
            Text(text = "Jetpack Compose", modifier = Modifier.align(Alignment.End))

            Row(modifier = Modifier.background(Color.Cyan)) {
                //对于水平布局中子项,Modifier.align 只能设置自己在垂直方向的位置.
                Text(text = "Jetpack Compose222", modifier = Modifier.align(Alignment.Bottom))
            }
        }

    }
}