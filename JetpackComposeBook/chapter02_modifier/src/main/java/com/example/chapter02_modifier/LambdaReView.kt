package com.example.chapter02_modifier


/**
 * kotlin lambda 复习
 */
fun main() {
    val list = listOf<String>("apple","banana","pear","grape")
    val lambda = {friut:String -> friut.length}
    list.maxBy(lambda)
    list.maxBy {
        it.length
    }

    //简化: 直接将lambda传入进去: 整体作为一个参数
    list.maxBy({friut:String -> friut.length})

    //Kotlin 规定：如果lambda 是函数唯一的一个参数 最后一个参数的时候,可以将lambda表达式移动到函数括号外。
    list.maxBy(){friut:String -> friut.length}

    //如果lambda参数是函数的唯一一个参数的,并且lambda放到函数括号外,还可以将函数的括号省略.
    list.maxBy{friut:String -> friut.length}

    //进一步简化:由于kotlin拥有出色的类型推导机制,lambda 表达式中的参数列表其实大多数情况下不必声明参数类型
    //所以进一步简化为:
    list.maxBy {friut -> friut.length}

    //最后:当lambda表达式的参数列表中只有一个参数的时候,（ { 参数名1: 参数类型 , 参数名2: 参数类型 -> 函数体 }）也不必申明参数名,而是可以使用
    //it 单个参数的隐式名称 来替代.所以可以进一步简化为如下:
    list.maxBy {
        it.length
    }

    val sb = java.lang.StringBuilder().build01 {
        this.append("Start eating fruits.\n")
        for (fruit in list) {
            append(fruit).append("\n")
        }
    }
    val toString = sb.toString()
}


fun StringBuilder.build01(block: StringBuilder.() -> Unit) : StringBuilder {
    /// 在 函数类型 前面加上 ClassName. 就表示这个 函数类型 是定义在哪个类中.
    //  此处的 函数类型是 : () -> Unit
    // 那么将函数类型定义到 StringBuilder 类中有什么好处呢?
    //  好处就是当我们调用 build 函数时传入 lambda 表达式会自动拥有 StringBuilder 的上下文. 这也是 apply函数的实现方式.
    block()
    return this
}



