package ru.korobeynikov.p43stabilitycollectionsstateholderslambdas

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@Stable
class MyHolder {

    private val _isVisible = MutableStateFlow(false)
    val isVisible = _isVisible.asStateFlow()

    fun toggle() {
        _isVisible.update { !it }
    }
}

class MyHolderStable {

    var isVisible by mutableStateOf(true)
        private set

    fun toggle() {
        isVisible = !isVisible
    }
}