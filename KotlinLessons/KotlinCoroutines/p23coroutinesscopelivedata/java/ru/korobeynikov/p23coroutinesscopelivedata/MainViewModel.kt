package ru.korobeynikov.p23coroutinesscopelivedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _liveData = MutableLiveData<String>()
    val liveData = _liveData as LiveData<String>

    init {
        viewModelScope.launch {
            while (true) {
                delay(1000)
                _liveData.value = "work"
            }
        }
    }
}