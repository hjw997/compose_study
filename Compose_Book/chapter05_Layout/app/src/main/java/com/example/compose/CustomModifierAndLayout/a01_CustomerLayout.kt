package com.example.compose.CustomModifierAndLayout

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.unit.Constraints


// 自定义容器布局Row
@Composable
fun MyCustomRowLayout(modifier: Modifier = Modifier,content: @Composable () -> Unit) {
    //1.布局进行策略测量
   val measurePolicy =  MeasurePolicy{measureables, constraints ->
        //2.测量子元素 constraints 相当于盒子模型-这里会是父布局的宽高。

        val placeables = measureables.map { measurable ->
            //测量子元素-设置宽高 - 给个我们自己的约束
            measurable.measure(Constraints(
                minWidth = 0,
                maxWidth = constraints.maxWidth,
                minHeight = 0,
                maxHeight = constraints.maxHeight
            ))
        }

        var xPosition = 0
        //3.放置 children
        layout(constraints.maxWidth, constraints.maxHeight) {
            placeables.forEach { placeable ->
                placeable.placeRelative(xPosition, 0)
                xPosition += placeable.width
            }
        }

    }

    Layout(content = content, modifier = modifier, measurePolicy = measurePolicy)
}

@Composable
fun MyCustomColumnLayout(modifier: Modifier = Modifier,content: @Composable () -> Unit) {

    val measurePolicy = MeasurePolicy { measureables, constraints ->

        /// 容器的这种 measureables 是个集合
        val placeables = measureables.map { measurable ->
            //测量子组件们 结果放在 placeables 中
            measurable.measure(constraints)
        }
        //开始布局 摆放子组件们。 因为是 Column 垂直累计叠加。
        layout(constraints.maxWidth, constraints.maxHeight) {
            var yPosition = 0
            placeables.forEach { placeable ->
                placeable.placeRelative(0, yPosition)
                yPosition += placeable.height
            }
        }
    }

    /// 要明白上面的 这个 表达式 怎么来的？
    /**
     * MeasurePolicy 是个接口。默认需要实现 measure 方法。
     * 函数实现要 返回一个 MeasureResult ，函数不会默认最后返回， 只有 lambda 表达式中的 最后一句才会作为 表达式的返回值。
     */
    val policy = object : MeasurePolicy {
        override fun MeasureScope.measure(
            measurables: List<Measurable>,
            constraints: Constraints
        ): MeasureResult {
            /// 容器的这种 measureables 是个集合
            val placeables = measurables.map { measurable ->
                //测量子组件们 结果放在 placeables 中
                measurable.measure(constraints)
            }
            // 如果是函数是需要加 return 不然报错。
            return layout(constraints.maxWidth, constraints.maxHeight) {
                var yPosition = 0
                placeables.forEach { placeable ->
                    placeable.placeRelative(0, yPosition)
                    yPosition += placeable.height
                }
            }
        }
    }

    Layout(
        modifier = modifier,
        content = content,
        measurePolicy = object : MeasurePolicy {
            override fun MeasureScope.measure(
                measurables: List<Measurable>,
                constraints: Constraints
            ): MeasureResult {
                /// 容器的这种 measureables 是个集合
                val placeables = measurables.map { measurable ->
                    //测量子组件们 结果放在 placeables 中
                    measurable.measure(constraints)
                }
                //开始布局 摆放子组件们。 因为是 Column 垂直累计叠加。
                return  layout(constraints.maxWidth, constraints.maxHeight) {
                    var yPosition = 0
                    placeables.forEach { placeable ->
                        placeable.placeRelative(0, yPosition)
                        yPosition += placeable.height
                    }
                }
            }
        }
    )
}