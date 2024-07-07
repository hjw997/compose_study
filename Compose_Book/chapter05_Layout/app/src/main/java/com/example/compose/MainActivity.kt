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

import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.CustomModifierAndLayout.MyCustomRowLayout
import com.example.compose.CustomModifierAndLayout.TestFirstBaseline
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
            
            //使用自定义布局
            //TestMyRow()
            TestFirstBaseline()

        }
    }
}

@Composable
fun TestMyRow() {
    Box {
        MyCustomRowLayout(modifier = Modifier
            .background(Color.Red)
            .wrapContentHeight()) {
            Text(text = "Hello")
            Text(text = "World")
        }
    }
}


@Preview
@Composable
fun WelcomePageLightPreview() {
    WelcomePage()
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


@Preview(showBackground = true)
@Composable
fun TestMyRowPreview() {
    TestMyRow()
}