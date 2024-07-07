package com.example.compose.CustomModifierAndLayout

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Modifier 的 layout 修饰符
 */

fun Modifier.firstBaseLineToTop(
    firstBaselineToTop: Dp
) = this.then(Modifier.layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)
    check(placeable[FirstBaseline]  != AlignmentLine.Unspecified)
    //获取基线的高度--原来就有的值。
    val firstBaseline = placeable[FirstBaseline]

    // 再原来基础上要增加 多少
    val delta = firstBaselineToTop.roundToPx() - firstBaseline
    val height = placeable.height + delta //组件原来的高度 + 调整的高度 就是整个实际高度。

    Log.i("TAG", "firstBaseline: $firstBaseline")
    Log.i("TAG", "firstBaselineToTop: $firstBaselineToTop")
    Log.i("TAG", "delta: $delta")
    Log.i("TAG", "height: $height")
    layout(placeable.width,height){
        //摆放的时候 相对原来的位置做调整。
        placeable.placeRelative(0,delta)
    }
})

@Composable
fun TestFirstBaseline(){
    Row{
        Text("Hi there!",modifier = Modifier
            .background(Color.Green)
        )

        Spacer(modifier = Modifier.width(20.dp))

        Text("Hi there!",modifier = Modifier
            .background(Color.Green)
            .firstBaseLineToTop(32.dp)
        )

    }

}