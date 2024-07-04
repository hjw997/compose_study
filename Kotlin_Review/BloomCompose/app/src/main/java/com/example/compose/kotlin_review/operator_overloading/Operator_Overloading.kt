package com.example.compose.kotlin_review.operator_overloading


/**
 * 关于运算符重载：
 * 官方文档： https://kotlinlang.org/docs/operator-overloading.html#invoke-operator
 *
 */
fun main() {
    // () 运算符重载：invoke
    val func = fun() {

    }
    func.invoke()
    //invoke 也可以用 () .
    func()

    "abc".invoke {
        println()
    }
    //上面的简化调用：
    "abc" {
        println()
    }

    "abc" {
        //调用的是String运算符重载的 第二个方法。
        "charset"("utf-8")
    }

    val res =
        "width:200px;height:200px;background:red;line-height:200px;text-align:center;".trimIndent()
    println(res)


    /**
    https://book.kotlincn.net/text/strings.html#%E5%A4%9A%E8%A1%8C%E5%AD%97%E7%AC%A6%E4%B8%B2
    多行字符串
    多行字符串可以包含换行以及任意文本。 它使用三个引号（"""）分界符括起来，
    内部没有转义并且可以包含换行以及任何其他字符：
     */
    "style" {
        """
         width:200px;
         height:200px;
         background:red;
         line-height:200px;
         text-align:center;
         """.trimIndent()
    }
}

//"abc" {"charset"("utf-8")} 这种到底是个啥？？
//运算符重载1
operator fun String.invoke(block: String.() -> Unit) {
    block()
}

//运算符重载2
operator fun String.invoke(value: Any) {
    println(value)
}