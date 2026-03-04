package ru.korobeynikov.viewmodelapplication.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.korobeynikov.viewmodelapplication.data.SomeRepository

class HomeViewModel(private val repository: SomeRepository) : ViewModel() {

    private val _counter = MutableStateFlow(0)
    var counter: StateFlow<Int> = _counter

    fun onCounterClick() {
        _counter.value++
    }
}