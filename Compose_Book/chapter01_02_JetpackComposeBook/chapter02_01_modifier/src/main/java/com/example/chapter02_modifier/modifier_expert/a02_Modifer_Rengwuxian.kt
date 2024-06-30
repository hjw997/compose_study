package com.example.chapter02_modifier.modifier_expert

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * 扔物线 课堂演练：
 * 01：Modifier 是什么？
 * 了解 Modifier 接口 和 Modifier 的关系。
 * 作为 Modifier 的链条的起点。
 */
@Composable
fun ModifierCourse01() {
    // companion （在哪个类或者接口中申明） object(来声明单例） : Modifier {
    Modifier ///拿到最简单(光秃秃的） 一个对象。
    Modifier.Companion //伴生对象。
    //以上都表示 Modifier 的 单例。

    /// companion object : Modifier { 这种写法 常用来创建类似 Java中的静态变量方法。
    Modifier.size(10.dp)

    //CustomTest01(modifier = Modifier.background(Color.Blue).size(40.dp))
    CustomTest01()

    //padding 扩展函数
    Modifier.padding(10.dp)
}


@Composable
fun CustomTest01(modifier: Modifier = Modifier) { ///可以给它一个默认的Modifier。
    ///Compose 官方建议：compose 参数第一个为modifier。
    Box(modifier) {

    }
}

@Composable
fun ModifierCourse02() {
    /// Modifier 是 如何链接在一起的
    Modifier
        .padding(8.dp)
        .background(Color.Red)

}