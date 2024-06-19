package com.example.composetest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _count = MutableLiveData<Int>()
    private val _doubleCount = MutableLiveData<Int>()

    val count : LiveData<Int> = _count
    val doubleCount = _doubleCount

    fun incrementCount() {
        _count.value = (_count.value ?: 0).plus(1)
    }
    fun incrementDoubleCount() {
        _doubleCount.value = (_doubleCount.value ?: 0).plus(2)
    }
}