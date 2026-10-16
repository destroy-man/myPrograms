package ru.korobeynikov.p04viewmodel.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.korobeynikov.p04viewmodel.DataRepository

class MyViewModel(private val dataRepository: DataRepository) : ViewModel() {

    private val _data = MutableStateFlow("")
    val data: StateFlow<String> = _data

    fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            _data.value = dataRepository.loadData()
        }
    }

    override fun onCleared() {
        _data.value = ""
    }
}