package ru.korobeynikov.p33animationtransition

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.random.Random

class HomeViewModel : ViewModel() {

    val visitorCount = MutableStateFlow(0)

    init {
        viewModelScope.launch {
            while (isActive) {
                delay(2000)
                visitorCount.value = Random.nextInt(100, 500)
            }
        }
    }
}