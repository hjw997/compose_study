package com.example.compose.CustomModifierAndLayout

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet

@Composable
fun DecoupleConstraintLayout(
    modifier: Modifier = Modifier,
) {
    BoxWithConstraints {
       //更具横竖屏来设置约束。
       val constraints =  if (maxWidth < maxHeight){
            //竖屏
            coupleConstraints(10.dp)
        }else{
            //横屏
            coupleConstraints(50.dp)
        }
        ConstraintLayout(
            constraintSet = constraints,
            modifier = modifier
        ) {
            Button(onClick = { /*TODO*/ },
                modifier = Modifier.layoutId("button")) {
                Text(text = "我是按钮")
            }
            Text(text = "我是文本", modifier = Modifier.layoutId("text"))
        }
    }
}

private fun coupleConstraints(margin:Dp) : ConstraintSet {
    return ConstraintSet {
        ///
        val button = createRefFor("button")
        val text  = createRefFor("text")

        /// 定义一个个的约束
        constrain(button){
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
        constrain(text){
            top.linkTo(button.bottom)
        }

    }
}

