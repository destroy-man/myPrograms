package ru.korobeynikov.p04viewmodel.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.korobeynikov.p04viewmodel.DataRepository

class MyViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass == MyViewModel::class.java) {
            return MyViewModel(DataRepository()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}