package ru.korobeynikov.p12viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel

class HomeViewModelComposeState : ViewModel() {

    private val _counter = mutableIntStateOf(0)
    val counter: State<Int> = _counter

    fun onCounterClick() {
        _counter.intValue++
    }
}