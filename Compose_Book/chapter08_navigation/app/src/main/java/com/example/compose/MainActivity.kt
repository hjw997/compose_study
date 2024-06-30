/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.compose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose.ui.HomePage
import com.example.compose.ui.LoginPage
import com.example.compose.ui.WelcomePage
import com.example.compose.ui.theme.BloomTheme

class MainActivity : AppCompatActivity() {

    var theme: BloomTheme by mutableStateOf(BloomTheme.LIGHT)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //WelcomePage()
            AppNavigation()
        }
    }
}

@Preview
@Composable
fun WelcomePageLightPreview() {
    WelcomePage(null)
}

@Preview
@Composable
fun LoginPageLightPreview() {
    LoginPage()
}

@Preview
@Composable
fun HomePageLightPreview() {
    HomePage()
}

/**
 * 导航学习须知：
 * TODO：补充：
 */


/**
 * AppNavigation 作为父级Composable,内部置放Navigation 的相关内容。
 */
@Composable
fun AppNavigation() {
    /**
     * 用来创建并持有一个 NavHostController 对象，前面说过，NavHostController 是一个导航管理者，内部维护着页面跳转过程的回退栈。
     * NavController 被传入各个子页面来进行页面跳转。 所以需要在父级Scope 中创建，以方便共享。比如这里的 AppNavigation() 。
     */
    val navController = rememberNavController()

    /**
     * NavController 需要被一个NavHost持有，NavHost 提供容器内部页面的切换。
     * 在Fragment的场景中 NavHostFragment 充当这个容器。
     * 在Compose中可以使用名为 NavHost 的Composable。
     * navController 参数指向先前创建的NavController。
     * startDestination 参数指向作为起点的Destination。
     */
    NavHost(navController = navController, startDestination = "welcome") {

        composable("welcome") {
            //把NavController 传入各个页面中。
            WelcomePage(nvaController = navController)
        }

        composable("login") {
            LoginPage()
        }

        composable("home") {
            HomePage()
        }
    }
}
