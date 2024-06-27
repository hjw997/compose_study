package com.example.chapter02_modifier

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.chapter02_modifier.modifier_expert.MainLayout01
import com.example.chapter02_modifier.modifier_expert.MainLayout02
import com.example.chapter02_modifier.modifier_expert.MainLayout03
import com.example.chapter02_modifier.modifier_expert.ModifierCourse01
import com.example.chapter02_modifier.modifier_expert.WeightModifierDemo
import com.example.chapter02_modifier.ui.theme.JetpackComposeBookTheme

class MainActivity : ComponentActivity() {

    /// 平时我们给一个类声明半生对象：
    companion object {
        const val TAG:String = "TAG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeBookTheme {
                //ComposeBookModifierTest()
                ComposeModifierRengwuxianCouser()
            }
        }
    }
}


/**
 * 1.ComposeBook 上的Demo 演示：
 */
@Composable
fun ComposeBookModifierTest() {
    MainLayout01()
    MainLayout02()
    MainLayout03()
    WeightModifierDemo()
}

/**
 * 2. 扔物线课堂演示代码演练。
 */
@Composable
fun ComposeModifierRengwuxianCouser() {
    ModifierCourse01()
}