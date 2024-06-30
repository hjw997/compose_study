import android.os.Bundle
import android.os.Parcelable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.android.parcel.Parcelize

/**
 * 1. 最基本的一个基数案例开始：
 *    StatefulCompose
 */
@Preview
@Composable
fun CounterComponent_01() {

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        /// 用来基数的 state ，该state保持在组件内部
        var counter by remember { mutableStateOf(0) }

        Text( //1
            "Click the buttons to adjust your value:",
            Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Text( //2
            "$counter",
            Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = typography.h3
        )

        Row {
            Button(
                onClick = { counter-- },
                Modifier.weight(1f)
            ) {
                Text("-")
            }
            Spacer(Modifier.width(16.dp))
            Button(
                onClick = { counter++ },
                Modifier.weight(1f)
            ) {
                Text("+")
            }
        }

    }
}


@Preview
@Composable
fun CounterComponent() {

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        val count by remember {
            mutableStateOf(10)
        }
        /// 不能帮我们记住这个值 当重组的时候 这里 重新创建.
        /// var (c, setC) = mutableStateOf(20)

        /// 通过 remember 来记住.
        var (c, setC) = remember {
            mutableStateOf(20)
        }

        Button(
            onClick = {
                setC(c++)
            },
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
        ) {
            Text(text = "点击试试$c")
        }

        var counter by remember { mutableStateOf(0) }

        Text( //1
            "Click the buttons to adjust your value:",
            Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Text( //2
            "$counter",
            Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = typography.h3
        )

        Row {
            Button(
                onClick = { counter-- },
                Modifier.weight(1f)
            ) {
                Text("-")
            }
            Spacer(Modifier.width(16.dp))
            Button(
                onClick = { counter++ },
                Modifier.weight(1f)
            ) {
                Text("+")
            }
        }
    }

}

/***
 * 现在如果想 使用一个计数功能,不能计数中出现负数, 那么上面的 CounterComponent() 这个里面固话了一套 counter 的更新的逻辑,不能复用
 */
@Composable
fun CounterScreen() {

    /// 下面通过状态提升实现复用性. 改造后 counter 作为参数,状态管理逻辑由调用方自己实现
    var counter by remember {
        mutableStateOf(0)
    }
    CounterComponent(
        counter = counter,
        onIncrement = {
            counter++
        }
    ) {
        ///不能计数中出现负数. 这里就可以控制了
        if (counter > 0) {
            counter--
        }
    }
}


/**
 * 此时 计数器案例被改为了 无状态的了
 * stateless 组件。
 */
@Composable
fun CounterComponent(
    counter: Int, ///重组调用方传入当前需要显示的的计数
    onIncrement: () -> Unit, //向调用方调用单击加号的事件
    onDecrement: () -> Unit //减号的回调事件
) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text( //1
            "Click the buttons to adjust your value:",
            Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color.White
        )

        Text( //2
            "$counter",
            Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = typography.h3,
            color = Color.White
        )

        Row {
            Button(
                onClick = { onDecrement() },
                Modifier.weight(1f)
            ) {
                Text("-")
            }
            Spacer(Modifier.width(16.dp))
            Button(
                onClick = { onIncrement() },
                Modifier.weight(1f)
            ) {
                Text("+")
            }
        }
    }
}

/*************************** 数据类 data class 如果屏幕旋转的 Activity 重新创建等.
 * 需要实现 Parcelable 接口和注解.
 * ***********************************/
/// 注意：它的插件是： id 'kotlin-parcelize'
/// 可以参数文章：https://www.cnblogs.com/cps666/p/17356413.html
@Parcelize
data class City(val name:String,val country:String) : Parcelable
@Composable
fun CityScreen() {
    /**
     * rememberSaveable 可以再屏幕旋转 Activity 重新创建的时候 会保持住这个数据.
     * 是个增强版的 remember
     */
    val selectCountry  = rememberSaveable {
        mutableStateOf(City("Beijing","China"))
    }
}

/***************************  如果是 第三方数据 没有实现 Parcelable 接口等 可以用下的 方式实现.  ***********************************/
data class Car(val name:String,val brand:String)

object CarSaver : Saver<Car, Bundle> {
    //restore 恢复
    override fun restore(value: Bundle): Car? {
        return value.getString("name")?.let {name ->
            value.getString("brand")?.let { brand ->
                Car(name,brand)
            }
        }
    }

    override fun SaverScope.save(value: Car): Bundle? {
        return Bundle().apply {
            putString("name", value.name)
            putString("brand",value.brand)
        }
    }

}

@Composable
fun CarScreen() {
    val sleectCar = rememberSaveable(stateSaver = CarSaver) {
        mutableStateOf(Car("Madrid","BMW"))
    }
}
/*************************** mapSaver **************************************/
val CarSaverMap = run {
    val nameKey = "Name"
    val brandKey = "Country"
    mapSaver(
        save = {
            mapOf(
                nameKey to it.name,brandKey to it.brand)
        },
        restore = {
            Car(it[nameKey] as String, it[brandKey] as String)
        }
    )
}
@Composable
fun CarScreen01() {
    val sleectCar = rememberSaveable(stateSaver = CarSaverMap) {
        mutableStateOf(Car("Madrid","BMW"))
    }
}

/*************************** listSaver **************************************/
val CarSaverList_ = run {
    /// 此处要写泛型
    listSaver<Car,Any>(
        save = {
            listOf(it.name,it.brand)
        },
        restore = {
            Car(it[0] as String, it[1] as String)
        }
    )
}
/// 此处要写泛型
val CarSaverList =  listSaver<Car,Any>(
    save = {
        listOf(it.name,it.brand)
    },
    restore = {
        Car(it[0] as String, it[1] as String)
    }
)
@Composable
fun CarScreen02() {
    val selectCar = rememberSaveable(stateSaver = CarSaverList) {
        mutableStateOf(Car("Madrid","BMW"))
    }
}

/*************************** ViewModel   **************************************/
class CounterViewModel : ViewModel() {
    /**
     * 就是把 ViewModel 中的 LiveData 使用了 State了而已。
     */
    private val _counter = mutableStateOf(0)
    val counter: State<Int> = _counter

    fun increment() {
        _counter.value += 1
    }
    fun decrement() {
        if (_counter.value > 0) {
            _counter.value -=1
        }
    }
}

@Composable
fun CounterScreenWithViewModel() {
    /**
     * viewModel()是个@Composable 函数. 用于在 @Composable函数中创建 ViewModel.
     */
    val viewModel : CounterViewModel = viewModel()
    CounterComponent(counter = viewModel.counter.value, onIncrement = viewModel::increment, onDecrement = viewModel::decrement)
}
@Preview
@Composable
fun CounterScreenWithViewModelPreview() {
    CounterScreenWithViewModel()
}


/**
 * 事实上，remember 和 mutableStateOf 在 Composable 函数中几乎永远都是配套使用的。
 * remember函数的作用是让其包裹住的变量在重组的过程中得到保留，从而就不会出现变量被重新初始化的情况了
 */
@Composable
fun Counter(modifier: Modifier = Modifier) {

    val count = remember {
        mutableStateOf(0)
    }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${count.value}",
            fontSize = 50.sp
        )
        Button(
            onClick = { count.value++ }
        ) {
            Text(
                text = "Click me",
                fontSize = 26.sp
            )
        }
    }
}
@Preview
@Composable
fun CounterPreview() {
    Counter()
}


/*********************************** 状态提升:State Hoisting *****************/
@Composable
fun CallCounter(modifier: Modifier = Modifier) {
    var count by rememberSaveable { mutableStateOf(0) }
    Counter(
        count = count,
        onIncrement = { count++ },
        modifier
    )
}

@Composable
fun Counter(
    count: Int,
    onIncrement: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "$count",
            fontSize = 50.sp
        )
        Button(
            onClick = { onIncrement() }
        ) {
            Text(
                text = "Click me",
                fontSize = 26.sp
            )
        }
    }
}

/**
 * 通常意义上来讲，像这种状态向下传递、事件向上传递的编程模式，我们称之为 单向数据流模式（Unidirectional Data Flow）。
 * 而状态提升就是这种单向数据流模式在Compose中的具体应用。
 */

/************************* ViewModel 和 LiveData
 * 这段代码和Compose没有任何关系，完全是纯粹的ViewModel和LiveData的用法。
 * *************/
class MainViewModel : ViewModel() {
    private val _count = MutableLiveData<Int>()
    private val _doubleCount = MutableLiveData<Int>()

    val count: LiveData<Int> = _count
    val doubleCount: LiveData<Int> = _doubleCount

    fun incrementCount() {
        _count.value = (_count.value ?: 0).plus(1)
    }

    fun incrementDoubleCount() {
        _doubleCount.value = (_doubleCount.value ?: 0).plus(2)
    }
}

@Composable
fun CallCounter(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel()
) {
    /**
     * 我们需要将 LiveData 转换成 State才行，observeAsState()函数就是用来做这个事情的，参数中传入的0表示它的初始值
     */
    val count by viewModel.count.observeAsState(0)
    val doubleCount by viewModel.doubleCount.observeAsState(0)

    Column {
        Counter(
            count = count,
            onIncrement = { viewModel.incrementCount() },
            modifier.fillMaxWidth()
        )
        Counter(
            count = doubleCount,
            onIncrement = { viewModel.incrementDoubleCount() },
            modifier.fillMaxWidth()
        )
    }
}

