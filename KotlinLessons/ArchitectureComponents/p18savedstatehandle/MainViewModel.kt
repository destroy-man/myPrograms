package ru.korobeynikov.p18savedstatehandle

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class MainViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    //Прямое извлечение данных из SavedStateHandle
    //val data = savedStateHandle.get<String>("data")

    //Извлечение данных через Flow
    val data = savedStateHandle.getStateFlow("data", "")

    fun updateData(newData: String) {
        savedStateHandle["data"] = newData
    }
}