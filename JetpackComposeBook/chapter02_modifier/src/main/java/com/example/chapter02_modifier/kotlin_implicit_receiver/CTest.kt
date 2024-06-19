package com.example.chapter02_modifier.kotlin_implicit_receiver

class IntMultiplier(private var time:Int = 2) {

    // member extention function : 成员扩展函数. 即是成员函数,又是扩展函数.
    // 成员扩展函数有个问题: 是 Int 的扩展函数,又是 IntMultiplier 的成员, 那么调用的时候怎么调用?
    fun Int.multiply() = this * time
}

fun main() {
    /// 成员扩展函数有个问题: 是 Int 的扩展函数,又是 IntMultiplier 的成员, 那么调用的时候怎么调用?
    // 直接使用 如下报错. ❌
    // 3.multiply()

    // 即需要 IntMultiplier 对象,又需要 Int 类型才能调用 的双重 receiver

    val multiplier = IntMultiplier()
    // 也报错: 光有外面的 IntMultiplier,没有 Int . 缺一不可.
    //multiplier.multiply()

    ///改进方案:
    val mutiplierA = IntMutiplier_A()
    mutiplierA.runAsOuter {
        /// 不用写内部类,就实现了 双重 this 的环境.
        3.multiply()
    }

    // 也可以双重 this
    mutiplierA.runAsOuter {
        3.runB {
            multiply()
        }
    }

    mutiplierA.runAsOuter {
        runC(3) {
            multiply()
        }
    }

    //kotlin 提供的
    mutiplierA.apply {
        with(3) {
            multiply()
        }
    }

    /**
     * 通过函数类型的 参数,强行插入了一层 receiver
     */

}


///改进方案:
class IntMutiplier_A(private var time: Int = 2) {

    fun Int.multiply() = this * time
    fun runAsOuter(block:IntMutiplier_A.() -> Unit) {
        // 给参数设置 receiver 以后, block内部就有了this.
        block()
    }
}

fun Int.runB(block: Int.() -> Unit) {
    block()
}

/// 还可以不给 Int 扩展, 把 Int 当参数
fun runC(int: Int,block : Int.() -> Unit) {
    int.block()
}

/**
 * 通过这种方式可以给 block 代码块中 传入 implict receiver
 */
