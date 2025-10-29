package ru.korobeynikov.p33animationtransition

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

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