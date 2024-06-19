package com.example.chapter02_modifier.kotlin_implicit_receiver

class OuterClass {
    var outerInt = 1
    var commonInt = 2

    inner class InnerClass {
        var innerInt = 1
        var commonInt = 2
        fun innerMethod() {
            println(outerInt)
            println(innerInt)

            println(commonInt)
            println(this@OuterClass.commonInt)
        }
    }
}