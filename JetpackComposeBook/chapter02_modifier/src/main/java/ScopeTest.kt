import androidx.compose.foundation.layout.LayoutScopeMarker

@LayoutScopeMarker
class AScope{
    fun visitA(){}
}

class BScope{
    fun visitB(){}
}


fun funA(scope: AScope.() -> Unit) {
    scope(AScope())
}

fun funB(scope: BScope.() -> Unit) {
    scope(BScope())
}

fun main() {
    funA {
        funB {
            ///出现了 跨级访问.
          visitA()
        }
    }
}


