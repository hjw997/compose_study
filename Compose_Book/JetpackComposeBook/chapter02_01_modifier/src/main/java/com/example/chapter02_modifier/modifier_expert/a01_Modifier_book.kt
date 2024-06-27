package com.example.chapter02_modifier.modifier_expert

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.chapter02_modifier.R


/**
 * Modifier:
 * 01:
 * size background fillMaxXXXX
 *
 */
@Composable
fun MainLayout01() {
    Column {
        //1.size
        Row {
            Image(
                painter = painterResource(id = R.drawable.pic),
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp) //设置被修饰的组件的大小为 60x60
                    .clip(CircleShape) //将图片设置为圆形
            )
            Spacer(modifier = Modifier.width(10.dp))
            Image(
                painter = painterResource(id = R.drawable.pic),
                contentDescription = null,
                modifier = Modifier.size(100.dp, 120.dp)
            )
        }
        ///2.background
        Row {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(color = Color.Red)
            ) {
                Text(text = "纯色", modifier = Modifier.align(Alignment.Center))
            }

            Spacer(Modifier.width(10.dp))

            /// Brush 是 Compose 提供的用来创建线性渐变色的工具.
            val verticalGradientBrush =
                Brush.verticalGradient(colors = listOf(Color.Red, Color.Yellow, Color.Blue))
            Box(
                Modifier
                    .size(100.dp)
                    .background(brush = verticalGradientBrush)
            ) {
                Text(text = "渐变色", modifier = Modifier.align(Alignment.Center))
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        //3.fillMaxSize
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Red)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(Color.Gray)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(40.dp)
                    .background(Color.Blue)
            )
        }


    }
}

/**
 * Modifier:
 * 02:
 * border padding
 */
@Composable
fun MainLayout02() {
    Box(
        modifier = Modifier
            .padding(30.dp) ///外边距 --相当与 margin
            .border(2.dp, color = Color.Cyan, shape = RoundedCornerShape(10.dp)) //边框
            .padding(20.dp) //内边距
    ) {
        Spacer(
            modifier = Modifier
                .size(100.dp, 50.dp)
                .background(Color.Blue)
        )
    }
}

@Composable
fun MainLayout03() {
    Column {
        Box(
            modifier = Modifier
                .size(100.dp)
                .offset(200.dp, 150.dp)
                //offset { IntOffset(150,150) }
                .background(Color.Green)
        ) {

        }
    }
}

@Composable
fun WeightModifierDemo() {
    Box(modifier = Modifier) {
        Column(
            modifier = Modifier
                .width(300.dp)
                .height(200.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(Color.Blue)
            ){}
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(Color.Red)
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(Color.Gray)
            )
        }
    }
}


@Composable
fun MainLayoutTestScope() {
//    Text(text = "abc",Modifier.align(Alignment.CenterVertically))
//    Row {
//        Text(text = "abc",Modifier.align(Alignment.CenterVertically))
//        Column {
//            Text(text = "abc",Modifier.align(Alignment.CenterVertically))
//            Text(text = "abc",Modifier.align(Alignment.CenterHorizontally))
//        }
//    }
}

