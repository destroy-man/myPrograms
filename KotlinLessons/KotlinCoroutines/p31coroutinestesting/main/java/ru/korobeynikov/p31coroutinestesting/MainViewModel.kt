package ru.korobeynikov.p31coroutinestesting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val showDialog = MutableStateFlow(false)

    fun showAndHideDialog() {
        showDialog.value = false
        viewModelScope.launch {
            delay(1000)
            showDialog.value = true
            delay(3000)
            showDialog.value = false
        }
    }
}