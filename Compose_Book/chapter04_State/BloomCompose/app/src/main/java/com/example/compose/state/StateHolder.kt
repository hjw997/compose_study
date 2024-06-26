package com.example.compose.state

import android.content.res.Resources
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.compose.R
import com.example.compose.ui.BottomBar
import com.example.compose.ui.ImageItem


/**
 * 使用一个数据类或者普通的类管理状态也是可以的。 称为：StateHolder （状态容器）
 * StateHolder 一般使用remember 存储在 Composable 中。
 */
class CounterState {

    private val _counter = mutableStateOf(0)
    val counter: State<Int> get() = _counter

    fun increment() {
        _counter.value += 1
    }
    fun decrement() {
        _counter.value -=1
    }
}


class MyAppState(
    val scaffoldState: ScaffoldState,
    val navController: NavHostController,
    private val resources: Resources
) {

    val bottomBarTabs = mutableStateOf(listOf(
        ImageItem("Home", R.drawable.ic_home),
        ImageItem("Favorites", R.drawable.ic_favorite_border),
        ImageItem("Profile", R.drawable.ic_account_circle),
        ImageItem("Cart", R.drawable.ic_shopping_cart)
    ))
    /// 决定什么时候显示bottomBar.
    val shouldShowBottomBar: Boolean
        get() {
            return true
        }

    /**
     * 导航逻辑，是UI逻辑的一种类型。
     */
    fun navigateToBottomBarRoute(route: String) {

    }

    /**
     * 基于资源显示 snackBar
     */
    fun showSnackBar(message: String) {

    }

}

/**
 * StateHolder 一般使用remember 存储在 Composable 中。
 */
@Composable
fun rememberMyAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController(),
    resources: Resources = LocalContext.current.resources
) = remember(scaffoldState, navController, resources){
    MyAppState(scaffoldState,navController,resources)
}

@Composable
fun MyApp() {
    val myAppState = rememberMyAppState()
    Scaffold(scaffoldState = myAppState.scaffoldState, bottomBar = {
        if (myAppState.shouldShowBottomBar) {
            BottomBar()
        }
    }) {
        NavHost(navController = myAppState.navController, startDestination = "initial") {

        }
    }
}